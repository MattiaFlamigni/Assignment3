#include "Lcd.h"
#include "Arduino.h"

#include <LiquidCrystal_I2C.h>
#include <Wire.h>
#include <String.h>
LiquidCrystal_I2C lcd = LiquidCrystal_I2C(0x27,20,4); 


Lcd::Lcd(int sda, int scl) {
    this->sda = sda;
    this->scl = scl;
    lcd.begin(20,4);
    lcd.backlight();
    pinMode(9,OUTPUT);
    digitalWrite(9,50);
    lcd.setCursor(0,0);
    
}

void Lcd::display(char *msg){
    lcd.print(msg);
}
void Lcd::display(int msg){
    lcd.print(msg);
}

void Lcd::twoLineText(char *msg) {
    lcd.clear();

    // Calcola la lunghezza totale del messaggio
    int msgLength = strlen(msg);

    // Trova la posizione del primo spazio dalla metà del messaggio
    int spacePosition = -1;
    for (int i = msgLength / 2; i < msgLength; i++) {
        if (msg[i] == ' ') {
            spacePosition = i;
            break;
        }
    }

    // Se trovi uno spazio, divide il messaggio in modo da non interrompere una parola
    if (spacePosition != -1) {
        // Salva la prima metà del messaggio
        char firstHalf[spacePosition + 1];
        strncpy(firstHalf, msg, spacePosition);
        firstHalf[spacePosition] = '\0';

        // Salva la seconda metà del messaggio
        char secondHalf[msgLength - spacePosition + 1];
        strncpy(secondHalf, msg + spacePosition + 1, msgLength - spacePosition);
        secondHalf[msgLength - spacePosition] = '\0';

        // Stampa la prima metà del messaggio nella prima riga
        lcd.setCursor(0, 0);
        lcd.print(firstHalf);

        // Stampa la seconda metà del messaggio nella seconda riga
        lcd.setCursor(0, 1);
        lcd.print(secondHalf);
    } else {
        // Se non trovi uno spazio, stampa il messaggio intero nella prima riga
        lcd.setCursor(0, 0);
        lcd.print(msg);
    }
}






void Lcd::clear(){
    lcd.clear();
}
