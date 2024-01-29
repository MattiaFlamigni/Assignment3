package rivermonitoringservice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
    //private SerialCommChannel serialCommChannel;

    private static final double WL1 = 0.2;
    private static final double WL2 = 0.4;
    private static final double WL3 = 0.6;
    private static final double WL4 = 0.8;

    public static void main(String[] args) throws Exception {
        //MqttSubscriber subscriber = new MqttSubscriber();

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
                    int waterLevel = Integer.parseInt(new String(message.getPayload()));
                    System.out.println("Livello dell'acqua: " + waterLevel);
                    String valveOpen = decideValveOpen(waterLevel);

                    try {
                        
                        channel.sendMsg(valveOpen);
    

                        System.out.println("Messaggio inviato: " + valveOpen);

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

    

    private static String decideValveOpen(int waterLevel) { //da rifare con struttura if corretta
        String valveOpeningLevel = "5";

        /*if (waterLevel >= WL1 && waterLevel <= WL2) {
            valveOpeningLevel = "25";
        } else if (waterLevel < WL1) {
            valveOpeningLevel = "0";
        } else if (waterLevel > WL2 && waterLevel <= WL3) {
            valveOpeningLevel = "25";
        } else if (waterLevel > WL3 && waterLevel <= WL4) {
            valveOpeningLevel = "50";
        } else if (waterLevel > WL4) {
            valveOpeningLevel = "200";
        }*/

        return valveOpeningLevel;
    }
}
