#ifndef __SYSTEMMODETASK__
#define __SYSTEMMODETASK__

#include "components/servo_motor.h"
#include "components/MyLCD.h"
#include "components/Button.h"
#include "components/Potentiometer.h"

class SystemModeTask: public Task{
    public:
        SystemModeTask();
        void tick();

    private:
        enum {AUTOMATIC, MANUAL} state;
        void setState(int state);
        ServoMotor* servo;
        MyLCD* lcd;
        Button* button;
        Potentiometer* potentiometer;
        int openingLevel;
};

#endif