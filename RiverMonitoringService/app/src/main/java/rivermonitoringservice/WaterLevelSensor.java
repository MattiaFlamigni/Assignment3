package rivermonitoringservice;
import rivermonitoringservice.api.WaterLevelSensorApi;



public class WaterLevelSensor implements WaterLevelSensorApi{
    private static WaterLevelState state;
    private static final double WL1 = 2;
    private static final double WL2 = 4;
    private static final double WL3 = 6;
    private static final double WL4 = 8;
    private static final int F1 = 2000;
    private static final int F2 = 6000;
    private static MqttPubblisher mqttPubblisher;
    private static String valveOpeningLevel = "5";
    private static int rawWaterLevel;

    public WaterLevelSensor(){
        mqttPubblisher = new MqttPubblisher("tcp://broker.mqtt-dashboard.com:1883", "JavaPublisher", "frequency");
    }

    public void updateWaterLevel(int waterLevel) throws Exception{

        rawWaterLevel = waterLevel;

        if(WL1<=waterLevel && waterLevel<WL2){
            /*state normal */
            state = WaterLevelState.NORMAL;
            valveOpeningLevel = "25";
            mqttPubblisher.sendMsg(Integer.toString(F1));
        }else if(waterLevel<WL1){
            state = WaterLevelState.TOO_LOW;
            valveOpeningLevel = "0";
            mqttPubblisher.sendMsg(Integer.toString(F1));
        }else{
            if(WL2<=waterLevel && waterLevel<=WL3){
                state = WaterLevelState.PREE_TOO_HIGH;
                valveOpeningLevel = "25";
                mqttPubblisher.sendMsg(Integer.toString(F2));
            }

            if(WL3<waterLevel && waterLevel<=WL4){

                state = WaterLevelState.TOO_HIGH;
                mqttPubblisher.sendMsg(Integer.toString(F2));
                valveOpeningLevel = "50";
            }

            if(WL4<waterLevel){
                state = WaterLevelState.TOO_HIGH_CRITICAL;
                valveOpeningLevel = "100";
                mqttPubblisher.sendMsg(Integer.toString(F2));
            }
        }
    }


    public WaterLevelState getState() {
        return state;
    }

    public static String getLevel() {
        return valveOpeningLevel;
    }

    public static String getWaterLevel(){
        return Integer.toString(rawWaterLevel);
    }

    

}
