package dashboard;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler{
    private String apertura; 

    public MyHandler(){}

    @Override
    public void handle(HttpExchange t) throws IOException {
        // Gestisci la richiesta qui
        String response = apertura;

       
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }



    public void inviaDato(String value){
        this.apertura = value;
        System.out.println(apertura);
    }

}
