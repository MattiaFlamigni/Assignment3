package rivermonitoringservice;

public class App {

    private static MqttSubscriber mqttSubscriber;
    

    public static void main(String[] args) {
        try {
            mqttSubscriber = new MqttSubscriber("tcp://broker.mqtt-dashboard.com:1883", "JavaSubscriber", "WaterLevel"   );
            mqttSubscriber.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
