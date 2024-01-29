package rivermonitoringservice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
    //private SerialCommChannel serialCommChannel;

    private static final double WL1 = 2;
    private static final double WL2 = 4;
    private static final double WL3 = 6;
    private static final double WL4 = 8;

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
        String valveOpeningLevel = "0";

        if(WL1<=waterLevel && waterLevel<WL2){
            valveOpeningLevel = "25";
        }else if(waterLevel<WL1){
            valveOpeningLevel = "0";
        }else{
            if(WL2<=waterLevel && waterLevel<=WL3){
                ;
            }

            if(WL3<waterLevel && waterLevel<=WL4){
                valveOpeningLevel = "50";
            }

            if(WL4<waterLevel){
                valveOpeningLevel = "100";
            }
        }

        return valveOpeningLevel;
    }
}
