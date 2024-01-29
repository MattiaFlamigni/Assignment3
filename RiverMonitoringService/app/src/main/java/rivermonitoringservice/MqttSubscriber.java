package rivermonitoringservice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import rivermonitoringservice.api.ValveControllerApi;
import rivermonitoringservice.api.WaterLevelSensorApi;



public class MqttSubscriber {
    // private SerialCommChannel serialCommChannel;

    private static ValveControllerApi valveController = new ValveController();
    private static WaterLevelSensorApi waterLevelSensor = new WaterLevelSensor();

    public static void main(String[] args) throws Exception {
        // MqttSubscriber subscriber = new MqttSubscriber();

        String broker = "tcp://broker.mqtt-dashboard.com:1883";
        String clientId = "JavaSubscriber";
        String topic = "WaterLevel";
        CommChannel channel = new SerialCommChannel("COM4", 9600);

        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            client.setCallback(new MqttCallback() {

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Messaggio ricevuto: " + new String(message.getPayload()));
                    // ottengo il messaggio mqtt e lo converto in int
                    int waterLevel = Integer.parseInt(new String(message.getPayload()));

                    try {
                        // calcolo lo stato del livello dell'acqua e lo invio al controller della
                        // valvola
                        waterLevelSensor.updateWaterLevel(waterLevel);
                        String valveOpen = valveController.adjustValve(waterLevelSensor.getState());
                        channel.sendMsg(valveOpen);

                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                public void connectionLost(Throwable cause) {
                    System.out.println("Connessione persa al broker MQTT.");
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            client.connect(options);
            client.subscribe(topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
