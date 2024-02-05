package rivermonitoringservice;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpDataSender {
    public static void sendData(String valveOpen, WaterLevelState waterLevelState) throws Exception {
        String url = "http://localhost:8001/status";

        // Creazione dell'oggetto che rappresenta i dati da inviare
        DataToSend dataToSend = new DataToSend(valveOpen, waterLevelState);

        // Serializzazione dell'oggetto in formato JSON
        Gson gson = new Gson();
        String jsonData = gson.toJson(dataToSend);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] postDataBytes = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(postDataBytes, 0, postDataBytes.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Dati inviati con successo tramite HTTP.");
            System.out.println("ValveOpen: " + valveOpen);
            System.out.println("WaterLevelState: " + waterLevelState);
        } else {
            System.out.println("Errore durante l'invio dei dati tramite HTTP. Codice di risposta: " + responseCode);
        }
    }

    // Classe per rappresentare i dati da inviare
    static class DataToSend {
        private String valveOpen;
        private WaterLevelState waterLevelState;


        public DataToSend(String valveOpen, WaterLevelState waterLevelState) {
            this.valveOpen = valveOpen;
            this.waterLevelState = waterLevelState;
        }
    }
}
