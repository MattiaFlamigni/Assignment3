package dashboard;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataReader {
    public static void main(String[] args) {
        try {
            String response = getResponse();
            Gson gson = new Gson();
            try {
                DataReceived dataReceived = gson.fromJson(response, DataReceived.class);
                System.out.println("ValveOpen: " + dataReceived.getValveOpen());
                System.out.println("WaterLevelState: " + dataReceived.getWaterLevel());
            } catch (JsonSyntaxException e) {
                System.out.println("Errore durante il parsing del JSON: " + e.getMessage());
                System.out.println("JSON non valido: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getResponse() throws Exception {
        URL url = new URL("http://localhost:8001/status");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } finally {
            con.disconnect();
        }
    }

    public static class DataReceived {
        private static String valveOpen;
        private static WaterLevelState waterLevelState;

        public static String getValveOpen() {
            return valveOpen;
        }

        public static WaterLevelState getWaterLevel() {
            return waterLevelState;
        }
    }
}
