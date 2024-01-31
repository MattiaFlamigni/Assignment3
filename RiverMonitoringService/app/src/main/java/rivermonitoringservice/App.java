package rivermonitoringservice;

public class App {

    private static MqttSubscriber mqttSubscriber;
    private static MqttPubblisher mqttPubblisher;
    

    public static void main(String[] args) {
        try {
            mqttSubscriber = new MqttSubscriber("tcp://broker.mqtt-dashboard.com:1883", "JavaSubscriber", "WaterLevel"   );
            mqttPubblisher = new MqttPubblisher("tcp://broker.mqtt-dashboard.com:1883", "JavaPublisher", "Frequency");
            mqttSubscriber.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
