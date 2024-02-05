package rivermonitoringservice;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import rivermonitoringservice.HTTPServer.MyHandler;

public class App {

    private static MqttSubscriber mqttSubscriber;
    

    public static void main(String[] args) {
        try {
            mqttSubscriber = new MqttSubscriber("tcp://broker.mqtt-dashboard.com:1883", "JavaSubscriber", "WaterLevel"   );
            mqttSubscriber.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpServer server;
        HttpServer server1;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server1 = HttpServer.create(new InetSocketAddress(8001), 0);
            server.createContext("/endpoint", new MyHandler());
            server1.createContext("/status", new MyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            server1.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
