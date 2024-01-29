package rivermonitoringservice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
    //private SerialCommChannel serialCommChannel;


    /* il valore letto ha lo stato too-low, normal, too-high */
    private enum WaterLevelState {
        TOO_LOW, NORMAL, PREE_TOO_HIGH, TOO_HIGH, TOO_HIGH_CRITICAL
    } 

    private static WaterLevelState state; 


    




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
                        /* invia ad arduino il grado di apertura delle valvole */
                        channel.sendMsg(valveOpen);
    
                        /*debug */
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
            /*state normal */
           state = WaterLevelState.NORMAL;


            valveOpeningLevel = "25";
        }else if(waterLevel<WL1){
            state = WaterLevelState.TOO_LOW;
            valveOpeningLevel = "0";

        }else{
            if(WL2<=waterLevel && waterLevel<=WL3){
                state = WaterLevelState.PREE_TOO_HIGH;
                valveOpeningLevel = "25";
            }

            if(WL3<waterLevel && waterLevel<=WL4){

                state = WaterLevelState.TOO_HIGH;
                valveOpeningLevel = "50";
            }

            if(WL4<waterLevel){
                state = WaterLevelState.TOO_HIGH_CRITICAL;
                valveOpeningLevel = "100";
            }
        }


        System.out.println("Stato: " + state);  
        return valveOpeningLevel;
    }
}
