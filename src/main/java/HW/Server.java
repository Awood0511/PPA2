package HW;

import com.sun.net.httpserver.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class Server {

    HttpServer server;
    Connection connection;

    //create a socket and start listening for requests
    public boolean start(Connection con) {
        try {
            connection=con;
            server = HttpServer.create(new InetSocketAddress(5000), 0);
            server.createContext("/", new MainHandler());
            server.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not open socket at port 5000");
            return false;
        }
    }

    public void close(){
      server.stop(1);
    }

    //use this to set the connection to a mock on tests
    public void setConn(Connection conn){
      connection = conn;
    }

    public class MainHandler implements HttpHandler{

        @Override
        public void handle (HttpExchange httpExchange) throws IOException{
            String uri=httpExchange.getRequestURI().toString();
            String method= httpExchange.getRequestMethod();
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
          try{
            Map <String, List<String>> map = h.getRequestHeaders();
            int guests = -1;
            double dinnerAmount = -1.0;
	          for (Map.Entry<String, List<String>> entry : map.entrySet()) {
              String temp= entry.getValue().toString();
              temp=  temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
              if(entry.getKey().equalsIgnoreCase("dinnerAmount")){
                dinnerAmount= Double.parseDouble(temp);
              }
              if(entry.getKey().equalsIgnoreCase("guests")){
                guests=Integer.parseInt(temp);
              }
            }

            //check for missing arguments
            if(guests == -1 || dinnerAmount == -1.0 ){
              String response="One or more request headers were not set correctly";
              h.sendResponseHeaders(200,response.getBytes().length);
              OutputStream os= h.getResponseBody();
              os.write(response.getBytes());
              os.close();
            }
            else{
              String response;
              ppa1Function ppa1Function= new ppa1Function();
              double[] ret=ppa1Function.splitTheTipDB(dinnerAmount,guests,connection);
              if (ret[0] == -1)
                response=("There cannot be zero guests.\n");
              else
                response=("Each person pays $" + ret[0] + " and must unequally split a remainder of $" + ret[1] + ".\n");

              h.sendResponseHeaders(200,response.getBytes().length);
              OutputStream os= h.getResponseBody();
              os.write(response.getBytes());
              os.close();
				    }
          }
          catch(Exception e){
            String response="Could not parse request header variables";
            h.sendResponseHeaders(404,response.getBytes().length);
            OutputStream os= h.getResponseBody();
            os.write(response.getBytes());
            os.close();
          }
        }

        private void postBmi(HttpExchange h) throws IOException{
          try{
            Map <String, List<String>> map= h.getRequestHeaders();
            int feet=-1;
            int inches=-1;
            double weight=-1.0;
	          for (Map.Entry<String, List<String>> entry : map.entrySet()) {
              String temp = entry.getValue().toString();
              temp = temp.substring(temp.indexOf("[")+1,temp.indexOf("]"));
              if(entry.getKey().equalsIgnoreCase("feet")){
                  feet= Integer.parseInt(temp);
              }
              if(entry.getKey().equalsIgnoreCase("inches")){
                inches=Integer.parseInt(temp);
              }
              if(entry.getKey().equalsIgnoreCase("weight")){
                weight=Double.parseDouble(temp);
              }
            }

            //check for missing arguments
            if(inches ==-1 || feet==-1 || weight==-1.0 ){
              String response="One or more request headers were not set correctly";
              h.sendResponseHeaders(200,response.getBytes().length);
              OutputStream os= h.getResponseBody();
              os.write(response.getBytes());
              os.close();
            }
            else{
              ppa1Function ppa1Function= new ppa1Function();
              String ret=ppa1Function.bodymassDB(feet, inches, weight,connection);
              if (ret.equals("weightless"))
                  ret=("You must enter a weight over or equal to 30 pounds.\n");

              else if (ret.equals("heightless"))
                ret=("You must enter a height over or equal to 2 feet.\n");

              else {
                int parseIndex = ret.indexOf('|');
                ret=("Your BMI is " + ret.substring(parseIndex + 1) + " which is considered "
                  + ret.substring(0, parseIndex) + ".\n");
              }
              String response=ret;
              h.sendResponseHeaders(200,response.getBytes().length);
              OutputStream os= h.getResponseBody();
              os.write(response.getBytes());
              os.close();
				    }
          }
          catch(Exception e){
            String response="Could not parse request header variables";
            h.sendResponseHeaders(404,response.getBytes().length);
            OutputStream os= h.getResponseBody();
            os.write(response.getBytes());
            os.close();
          }
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
        public String formatIntoJSON(ResultSet rs){
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
