#include <Arduino.h>
#include "components/servo_motor.h"
#include "components/servo_motor_impl.h"
#include "config.h"
#include "Scheduler/Scheduler.h"
#include "Scheduler/Task.h"
#include "Tasks/SystemModeTask.h"



ServoMotor* servoMotor;
Scheduler sched;

void setup() {
  sched.init(50); //todo: definire un valore di tick
  Serial.begin(9600);
  servoMotor = new ServoMotorImpl(SERVO_PIN);
  servoMotor->on();
  Task* systemModeTask = new SystemModeTask();
  sched.addTask(systemModeTask);
}

void loop() {
  sched.schedule();
}
