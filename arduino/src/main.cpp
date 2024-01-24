#include <Arduino.h>


#include "components/servo_motor.h"
#include "components/servo_motor_impl.h"
#include "config.h"



ServoMotor* servoMotor;

void setup() {
  servoMotor = new ServoMotorImpl(SERVO_PIN);
  servoMotor->on();
}

void loop() {
  servoMotor->setPosition(0);
  delay(1000);
  servoMotor->setPosition(50);
  delay(1000);
  servoMotor->setPosition(100);
  delay(1000);
  servoMotor->setPosition(150);
  delay(1000);
}
