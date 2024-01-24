#include <Arduino.h>
#include "components/Sonar.h";
#include "config.h"


Sonar* sonar;
void setup() {

  pinMode(RED_LED_PIN, OUTPUT);
  Serial.begin(9600);
  sonar = new Sonar(ECHO_PIN, TRIG_PIN, SONAR_TIME);
  sonar->setTemperature(18);
}

void loop() {

  digitalWrite(RED_LED_PIN, HIGH);
  Serial.println(sonar->getDistance());
}

