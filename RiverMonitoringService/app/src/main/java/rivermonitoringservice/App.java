package rivermonitoringservice;

public class App {
    public static void main(String[] args) {
        try {
            MqttSubscriber mqttSubscriber = new MqttSubscriber();
            mqttSubscriber.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
