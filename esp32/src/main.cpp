#include "MqttTask.h"
#include "components/Sonar.h"
#include "config.h"
#include "components/Led.h" 

const char *ssid = "Wind3 HUB-E99111";
const char *password = "29190210";
const char *mqtt_server = "broker.mqtt-dashboard.com";
const int mqtt_port = 1883;

Sonar* sonar;
MqttTask mqttTask("WaterLevel", "Hello, Java Application!");
Led* greenLed;
Led* redLed;
void setup() {
    greenLed = new Led(GREEN_LED_PIN);
    redLed = new Led(RED_LED_PIN);
    

  Serial.begin(115200);
  sonar = new Sonar(TRIG_PIN, ECHO_PIN, SONAR_TIME);

  // Connessione WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connessione WiFi...");

    greenLed->switchOff();
    redLed->switchOn();

  }
    

  // Inizializza la classe MqttTask
  
  mqttTask.begin();
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    greenLed->switchOn();
    redLed->switchOff();
    // Puoi inviare dati al broker MQTT qui
    // Ad esempio, puoi ottenere il valore dal sensore di livello d'acqua (sonar) e inviarlo al broker MQTT
    float waterLevel = sonar->getDistance();
    String message = "Water level: " + String(waterLevel);
    Serial.println(message);

    //invia il messaggio al broker MQTT
    //MqttTask mqttTask("WaterLevel", message.c_str()); //TODO controllare se funziona   

    mqttTask.publish("WaterLevel", message.c_str()); 

    delay(1000);

  } else {
    greenLed->switchOff();
    redLed->switchOn();
    Serial.println("Connessione WiFi persa");
  }
}
