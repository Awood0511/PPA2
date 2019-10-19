package HW;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;

public class Server {

    HttpServer server;

    Server() {
        try {
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

    static class MainHandler implements HttpHandler{
        @Override
        public void handle (HttpExchange httpExchange) throws IOException{
            String temp = "Method: " + httpExchange.getRequestMethod() + "\n";
            temp = temp + "Path: " + httpExchange.getRequestURI();

            //parse URI
            //GET to split
            if(false){

            }
            else if(false){

            }
            else if(false){

            }
            else if(false){

            }
            else{
              home(httpExchange);
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
    }
}
