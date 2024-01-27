/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rivermonitoringservice;








/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
    public static void main(String[] args) {
        String broker = "tcp://broker.mqtt-dashboard.com:1883"; // Indirizzo del tuo broker MQTT
        String clientId = "JavaSubscriber";
        String topic = "WaterLevel"; // Il topic MQTT a cui il tuo dispositivo ESP32 pubblica i messaggi

        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            client.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Messaggio ricevuto: " + new String(message.getPayload()));
                    //qui inserire il codice per inviare il messaggio tramite la seriale

                    try {
                        SerialCommChannel serialCommChannel = new SerialCommChannel("COM4", 9600);
                        serialCommChannel.sendMsg(new String(message.getPayload()));
                        /*chiudi la porta seriale */
                        serialCommChannel.close();
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
