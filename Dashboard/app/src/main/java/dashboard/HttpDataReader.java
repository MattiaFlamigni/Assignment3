package dashboard;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataReader {
    public static void readData() throws Exception {
        String url = "http://localhost:8001/status";

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Leggi il contenuto ricevuto dalla richiesta HTTP
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Deserializza il JSON ricevuto in un oggetto DataReceived
            Gson gson = new Gson();
            DataReceived dataReceived = gson.fromJson(response.toString(), DataReceived.class);

            // Puoi ora utilizzare l'oggetto dataReceived per accedere ai dati ricevuti
            System.out.println("Dati ricevuti: " + dataReceived);
        } else {
            System.out.println("Errore durante la lettura dei dati tramite HTTP. Codice di risposta: " + responseCode);
        }
    }


    public static class DataReceived {
        private static WaterLevelState waterLevel;
        private static int valveOpen;

        public static WaterLevelState getWaterLevel() {
            return waterLevel;
        }

        public static int getValveOpen() {
            return valveOpen;
        }

        @Override
        public String toString() {
            return "DataReceived{" +
                    "waterLevel=" + waterLevel +
                    ", valveOpen=" + valveOpen +
                    '}';
        }
    }
}
