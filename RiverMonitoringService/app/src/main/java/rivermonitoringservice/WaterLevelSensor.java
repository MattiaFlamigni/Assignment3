package rivermonitoringservice;
import rivermonitoringservice.api.WaterLevelSensorApi;



public class WaterLevelSensor implements WaterLevelSensorApi{
    private static WaterLevelState state;
    private static final double WL1 = 2;
    private static final double WL2 = 4;
    private static final double WL3 = 6;
    private static final double WL4 = 8;

    public void updateWaterLevel(int waterLevel){
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
    }


    public WaterLevelState getState() {
        return state;
    }

    

}
