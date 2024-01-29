#include <Arduino.h>
#include "config.h"
#include "components/servo_motor_impl.h"
#include "components/MyLCD.h"
#include "components/ButtonImpl.h"
#include "Tasks/SystemModeTask.h"

SystemModeTask::SystemModeTask(){
    this->state = AUTOMATIC; 
    

    pinMode(3, HIGH);
    this->servo = new ServoMotorImpl(SERVO_PIN);
    this->lcd = new MyLCD(0x27, 8, 2);
    this->button = new ButtonImpl(BUTTON_PIN);
    this->potentiometer = new Potentiometer(POT_PIN);
    
    servo->on();
    servo->setPosition(0);
    lcd->initialize();
    lcd->clearDisplay();
    lcd->printMessage("0, Automatic");
}

void SystemModeTask::tick() {
    switch(state) {
        case AUTOMATIC:
            Serial.println("Automatic");
            lcd->clearDisplay();
            // Converti l'intero in una stringa
            itoa(openingLevel, buffer, 10); // 10 indica la base decimale
            lcd->printMessage(buffer);
            lcd->printMessage(", Automatic");
            
            if (debouncedButtonPress()) {
                this->setState(MANUAL);
            }
            break;

        case MANUAL:
            Serial.println("Manual");
            
            lcd->clearDisplay();
            itoa(openingLevel, buffer, 10); // 10 indica la base decimale
            lcd->printMessage(buffer);
            lcd->printMessage(", Manual");
            
            if (debouncedButtonPress()) {
                this->setState(AUTOMATIC);
            }
            break;
    }
}

bool SystemModeTask::debouncedButtonPress() {
    static unsigned long lastDebounceTime = 0;
    static const unsigned long debounceDelay = 50; // Tempo di debounce in millisecondi
    static int lastButtonState = LOW;

    unsigned long currentMillis = millis();
    
    if (currentMillis - lastDebounceTime > debounceDelay) {
        // Leggi lo stato del pulsante
        int buttonState = button->isPressed();
        
        // Se lo stato del pulsante è cambiato
        if (buttonState != lastButtonState) {
            lastDebounceTime = currentMillis;
            
            // Se il nuovo stato del pulsante è HIGH (premuto)
            if (buttonState == HIGH) {
                lastButtonState = buttonState;
                return true; // Rilevato un pressione stabile
            }
        }
    }
    
    lastButtonState = button->isPressed();
    return false; // Nessuna pressione stabile rilevata
}

void SystemModeTask::setState(int newState) {
    state = newState;
}
