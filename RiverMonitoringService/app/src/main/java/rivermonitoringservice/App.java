package rivermonitoringservice;

public class App {
    public static void main(String[] args) {
        try {
            MqttSubscriber mqttSubscriber = new MqttSubscriber("tcp://broker.mqtt-dashboard.com:1883", "JavaSubscriber", "WaterLevel"   );
            mqttSubscriber.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
