#include <Arduino.h> 
#include "components/Led.h"



Led* led;

void setup(){
    led = new Led(12); 
}


void loop(){
  led->switchOn();
  delay(1000);
  led->switchOff();  
  delay(1000);
}