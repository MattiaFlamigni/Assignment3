#include <Arduino.h>


#include "components/servo_motor.h"
#include "components/servo_motor_impl.h"
#include "config.h"



ServoMotor* servoMotor;

void setup() {
  Serial.begin(9600);
  servoMotor = new ServoMotorImpl(SERVO_PIN);
  servoMotor->on();
}

void loop() {
  
  /*legge la porta seriale*/
  if (Serial.available() > 0) {
    Serial.println("Hello world");
    int incomingByte = Serial.read();
    Serial.print("I received: ");
    Serial.println(incomingByte, DEC);
    if (incomingByte == 49) {
      servoMotor->on();
    } else if (incomingByte == 48) {
      servoMotor->off();
    }
  }


}
