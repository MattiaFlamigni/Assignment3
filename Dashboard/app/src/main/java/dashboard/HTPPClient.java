package dashboard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTPPClient {
    static HttpURLConnection con;
    
    public HTPPClient() throws Exception{
        URL url = new URL("http://localhost:8000/endpoint");
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
    }

    public static String getResponse() throws Exception{

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}