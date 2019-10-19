package HW;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;

public class Server {

    public void Server() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(5000), 0);
            server.createContext("/", new MainHandler());
            server.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     
    
      
    }
    static class MainHandler implements HttpHandler{
        @Override
        public void handle (HttpExchange httpExchange) throws IOException{
            String response = "Error";
            httpExchange.sendResponseHeaders(200,response.getBytes().length);
            OutputStream os= httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    
}

