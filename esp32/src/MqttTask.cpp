#include "MqttTask.h"
#include "config.h"

WiFiClient espClient;
PubSubClient client(espClient);

MqttTask::MqttTask(const char *topic, const char *payload) : topic(topic), payload(payload) {
    
}

void MqttTask::begin() {

    this->greenLed = new Led(GREEN_LED_PIN);
    this->redLed = new Led(RED_LED_PIN);



  xTaskCreatePinnedToCore(
      taskFunction,
      "mqttTask",
      4096,
      this,
      1,
      &taskHandle,
      1
  );
}

void MqttTask::taskFunction(void *parameter) {
  MqttTask *mqttTask = (MqttTask *)parameter;

  while (1) {
    if (!client.connected()) {
        mqttTask->greenLed->switchOff();
        mqttTask->redLed->switchOn();
        reconnect();
    }

  

    client.publish(mqttTask->topic, mqttTask->payload);

    vTaskDelay(1000 / portTICK_PERIOD_MS);
  }
}

void MqttTask::reconnect() {
  while (!client.connected()) {
    Serial.print("Connessione MQTT...");
    if (client.connect("ESP32Client")) {
      Serial.println("Connesso");
    } else {


      Serial.print("Riprovo in 5 secondi...");
      delay(5000);
    }
  }
}

void MqttTask::publish(const char *topic, const char *payload) {
  client.publish(topic, payload);
}
