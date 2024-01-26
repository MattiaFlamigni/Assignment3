#ifndef MQTT_TASK_H
#define MQTT_TASK_H

#include <WiFi.h>
#include <PubSubClient.h>
#include <FreeRTOS.h>
#include <task.h>
#include "components/Led.h"

class MqttTask {
public:
  MqttTask(const char *topic, const char *payload);

  void begin();
  void publish(const char *topic, const char *payload);

private:
  static void taskFunction(void *parameter);
  static void reconnect();

  const char *topic;
  const char *payload;
  TaskHandle_t taskHandle;
  Led* greenLed;
  Led* redLed;
};

#endif // MQTT_TASK_H
