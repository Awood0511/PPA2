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
            //GET to split
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
            statement=	statement = connection.createStatement();
            rSet=statement.executeQuery("SELECT * FROM bodymass");
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
        private void getSplitTheTip(HttpExchange h) throws IOException{
          Statement statement= null;
          ResultSet rSet=null;
          try{
            statement=	statement = connection.createStatement();
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
          response += "To call the function Split the Tip send a post request with these arguments\n";
          response += "\tvariable \tData Type\n";
          response += "\tdinnerAmount \tdouble\n";
          response += "\tguests \tint\n";
          response += "To call the function BMI send a post request with these arguments\n";
          response += "\tvariable \tData Type\n";
          response += "\tfeet \tint\n";
          response += "\tinches \tint\n";
          response += "\tweight \tdouble\n";
          h.sendResponseHeaders(200,response.getBytes().length);
          OutputStream os= h.getResponseBody();
          os.write(response.getBytes());
          os.close();
        }
    }
}
