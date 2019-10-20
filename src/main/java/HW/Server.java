package HW;

import com.sun.net.httpserver.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.sql.*;
public class Server {

    HttpServer server;
    Connection connection;
    Server(Connection con) {
        try {
            connection=con;
            server = HttpServer.create(new InetSocketAddress(5000), 0);
            server.createContext("/", new MainHandler());
            server.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close(){
      server.stop(1);
    }

    class MainHandler implements HttpHandler{

        @Override
        public void handle (HttpExchange httpExchange) throws IOException{
            String uri=httpExchange.getRequestURI().toString();
            String method= httpExchange.getRequestMethod().toString();
            String temp = "Method: " + httpExchange.getRequestMethod() + "\n";
            temp = temp + "Path: " + httpExchange.getRequestURI();

            //parse URI
            if(method.equals("GET") && (uri.equals("/splitthetip"))){
                getSplitTheTip(httpExchange);
            }
            else if(method.equals("GET") && (uri.equals("/bmi"))){
              getBmi(httpExchange);
            }
            else if(method.equals("POST") && (uri.equals("/splitthetip"))){
              postSplitTheTip(httpExchange);
            }
            else if(method.equals("POST") && (uri.equals("/bmi"))){
              postBmi(httpExchange);
            }
            else{
              home(httpExchange);
            }
        }

        private void getBmi(HttpExchange h) throws IOException{
          Statement statement= null;
          ResultSet rSet=null;
          try{
            statement = connection.createStatement();
            rSet=statement.executeQuery("SELECT * FROM bodymass");
          }
          catch(SQLException e){
            e.printStackTrace();
            System.out.println("Could not connect to the database.");
          }

          String response= formatIntoJSON(rSet);
          h.getResponseHeaders().set("Content-type","application/json");
          h.sendResponseHeaders(200,response.length());
          OutputStream os= h.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }

        private void getSplitTheTip(HttpExchange h) throws IOException{
          Statement statement= null;
          ResultSet rSet=null;
          try{
            statement = connection.createStatement();
            rSet=statement.executeQuery("SELECT * FROM splitTheTip");
          }
          catch(SQLException e){
            e.printStackTrace();
            System.out.println("Could not connect to the database. Terminating program.");
          }
          String response= formatIntoJSON(rSet);
          h.getResponseHeaders().set("Content-type","application/json");
          h.sendResponseHeaders(200,response.length());
          OutputStream os= h.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }

        private void postSplitTheTip(HttpExchange h) throws IOException{

        }

        private void postBmi(HttpExchange h) throws IOException{

        }

        private void home(HttpExchange h) throws IOException{
          String response = "Welcome to the http interface.\n";
          response += "To see a list of all Split The Tip database entries go to /splitthetip\n";
          response += "To see a list of all BMI database entries go to /bmi\n";
          response += "To call the function Split the Tip send a post request to /splitthetip with these arguments\n";
          response += "\tVariable \tData Type\n";
          response += "\tdinnerAmount \tdouble\n";
          response += "\tguests \t\tint\n";
          response += "To call the function BMI send a post request to /bmi with these arguments\n";
          response += "\tVariable \tData Type\n";
          response += "\tfeet \t\tint\n";
          response += "\tinches \t\tint\n";
          response += "\tweight \t\tdouble\n";
          h.sendResponseHeaders(200,response.getBytes().length);
          OutputStream os= h.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }

        //formats the result set for the bmi or split tip query into JSON
        private String formatIntoJSON(ResultSet rs){
          try{
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            boolean first = true;

            //return empty string if empty columns
            if(cols == 0){
              return "[]";
            }

            //start building return string
            String json = "[";
            while(rs.next()){
              if(!first){
                json += ",";
              }
              else{
                first = false;
              }

              //build the json object for this row based on what table its from
              json += "{";
              if(meta.getTableName(1).equals("bodymass")){
                json += "\"createdAt\":\"" + rs.getString("createdAt") + "\",";
                json += "\"feet\":" + rs.getInt("feet") + ",";
                json += "\"inches\":" + rs.getInt("inches") + ",";
                json += "\"weight\":" + rs.getDouble("weight") + ",";
                json += "\"bmi\":" + rs.getDouble("bmi") + ",";
                json += "\"bodytype\":\"" + rs.getString("bodytype") + "\"";
              }
              else{
                json += "\"createdAt\":\"" + rs.getString("createdAt") + "\",";
                json += "\"dinnerAmount\":" + rs.getDouble("dinnerAmount") + ",";
                json += "\"guests\":" + rs.getInt("guests") + ",";
                json += "\"costPerGuest\":" + rs.getDouble("costPerGuest") + ",";
                json += "\"remainder\":" + rs.getDouble("remainder");
              }
              json += "}";

            }
            json += "]";
            return json;
          }
          catch(SQLException e){
            return "Error reading result set.";
          }
        }
    }
}
