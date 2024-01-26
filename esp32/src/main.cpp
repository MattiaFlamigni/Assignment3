#include <Arduino.h>
#include "mqtt_client.h"
#include "config.h"
#include "components/Sonar.h"

// Setup MQTT client
const char* ssid = "Wind3 HUB-E99111";
const char* password = "29190210";
const char* mqtt_server = "broker.mqtt-dashboard.com";
const char* topic = "WaterLevel";
Sonar* sonar;
MQTT_Client mqtt_client(ssid, password, mqtt_server, topic);
void setup() {
    mqtt_client.setup();

    /*Serial.begin(9600);
    sonar = new Sonar(ECHO_PIN, TRIG_PIN, SONAR_TIME);*/
}

void loop() {
    mqtt_client.loop();

    /*Serial.println(String("Distance: ") + sonar->getDistance());
    delay(1000);*/

}
