package rivermonitoringservice.api;

import rivermonitoringservice.WaterLevelState;

public interface WaterLevelSensorApi {
    void updateWaterLevel(int waterLevel);
    WaterLevelState getState();
}
