#include <Arduino.h>
#include "config.h"
#include "components/servo_motor_impl.h"
#include "components/MyLCD.h"
#include "components/ButtonImpl.h"
#include "Tasks/SystemModeTask.h"


SystemModeTask::SystemModeTask(){
    this->servo = new ServoMotorImpl(SERVO_PIN);
    this->lcd = new MyLCD(0x27,8 , 2);
    this->button = new ButtonImpl(BUTTON_PIN);
    //this->potentiometer = new Potentiometer(POT_PIN);
    servo->on();
    servo->setPosition(0);
    lcd->initialize();
    lcd->clearDisplay();
    lcd->printMessage("0, Automatic");

    /*legge la porta seriale*/
    /*if (Serial.available() > 0) {
        Serial.println("Hello world");
        int incomingByte = Serial.read();
        Serial.print("I received: ");
        Serial.println(incomingByte, DEC);
        if (incomingByte == 49) {
            servoMotor->on();
        } else if (incomingByte == 48) {
            servoMotor->off();
        }
    }*/
}

void SystemModeTask::tick() {
    switch(state) {
        case AUTOMATIC:
            do{
                openingLevel = 0;//TODO inserire get per l'opening level dato dal river monitoring service
                servo->setPosition(openingLevel); 
                lcd->printMessage(openingLevel + ", Automatic");
            } while(!button->isPressed());
            this->setState(MANUAL);
        break;

        case MANUAL:
            do{
                //openingLevel = potentiometer->getValue();
                servo->setPosition(openingLevel); 
                lcd->printMessage(openingLevel + ", Manual");
            } while(!button->isPressed());
            this->setState(AUTOMATIC);         
    }
}

void SystemModeTask::setState(int state) {
    state = state;
}