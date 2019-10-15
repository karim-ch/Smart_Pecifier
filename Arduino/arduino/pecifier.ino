#include <Arduino.h>
#include <Scheduler.h>
#include <ESP8266WiFi.h>
#include <ThingSpeak.h>
#include <math.h>

#define WIFI_SSID "YOUR_SSID_HERE"
#define WIFI_PASSWORD "YOUR_PASSWORD_HERE"
WiFiClient client;

int on_off = 0;
int seuil = 38;

int a = D2;  //For displaying segment "a"
int b = D3;  //For displaying segment "b"
int c = D4;  //For displaying segment "c"
int d = D5;  //For displaying segment "d"
int e = D6;  //For displaying segment "e"
int f = D7;  //For displaying segment "f"
int g = D8;  //For displaying segment "g"


unsigned long myChannelNumber = "YOUR_6DIGITS_CHANNEL_Number";
unsigned int temperatureFieldNumber = 1;
unsigned int on_offFieldNumber = 2;
unsigned int seuilFieldNumber = 3;
const char * myReadAPIKey = "YOUR_READ_API_KEY";
const char * myWriteAPIKey = "YOUR_WRITE_API_KEY";


int led=3; //RX
int tempint=0;
//----------------------------------------------------------------------------------------------------------------------------------------

class DisplayTask : public Task {
protected:
    void loop()  {


if(on_off==1){//ON
         
          
          if(tempint==0){
             displayFirstDigit(0);  
             delay(10);
             turnOff();
             displaySecondDigit(0); 
             delay(10);
             turnOff();
          }
          else{
             displayFirstDigit(tempint%10);  
             delay(10);
             turnOff();
             displaySecondDigit(tempint/10); 
             delay(10);
             turnOff();
            }

}else{
  turnOff();
}

 }

void displayFirstDigit(int digit)
{
 digitalWrite(D0,HIGH);
 digitalWrite(D1,LOW);

  
 //Conditions for displaying segment a
 if(digit!=1 && digit != 4)
 digitalWrite(a,LOW);
 
 //Conditions for displaying segment b
 if(digit != 5 && digit != 6)
 digitalWrite(b,LOW);
 
 //Conditions for displaying segment c
 if(digit !=2)
 digitalWrite(c,LOW);
 
 //Conditions for displaying segment d
 if(digit != 1 && digit !=4 && digit !=7)
 digitalWrite(d,LOW);
 
 //Conditions for displaying segment e 
 if(digit == 2 || digit ==6 || digit == 8 || digit==0)
 digitalWrite(e,LOW);
 
 //Conditions for displaying segment f
 if(digit != 1 && digit !=2 && digit!=3 && digit !=7)
 digitalWrite(f,LOW);
 
 //Conditions for displaying segment g
 if (digit!=0 && digit!=1 && digit !=7)
 digitalWrite(g,LOW);
 
}


void turnOff()
{
  digitalWrite(a,HIGH);
  digitalWrite(b,HIGH);
  digitalWrite(c,HIGH);
  digitalWrite(d,HIGH);
  digitalWrite(e,HIGH);
  digitalWrite(f,HIGH);
  digitalWrite(g,HIGH);
}

void displaySecondDigit(int digit)
{
 digitalWrite(D0,LOW);
 digitalWrite(D1,HIGH);

  
 //Conditions for displaying segment a
 if(digit!=1 && digit != 4)
 digitalWrite(a,LOW);
 
 //Conditions for displaying segment b
 if(digit != 5 && digit != 6)
 digitalWrite(b,LOW);
 
 //Conditions for displaying segment c
 if(digit !=2)
 digitalWrite(c,LOW);
 
 //Conditions for displaying segment d
 if(digit != 1 && digit !=4 && digit !=7)
 digitalWrite(d,LOW);
 
 //Conditions for displaying segment e 
 if(digit == 2 || digit ==6 || digit == 8 || digit==0)
 digitalWrite(e,LOW);
 
 //Conditions for displaying segment f
 if(digit != 1 && digit !=2 && digit!=3 && digit !=7)
 digitalWrite(f,LOW);
 
 //Conditions for displaying segment g
 if (digit!=0 && digit!=1 && digit !=7)
 digitalWrite(g,LOW);
 
}
    
} DisplayTask;

//----------------------------------------------------------------------------------------------------------------------------------------


class ReadTask : public Task {
protected:
    void loop()  {

	//-------------GET ON-OFF------------------------
	on_off = ThingSpeak.readIntField(myChannelNumber, on_offFieldNumber, myReadAPIKey);
	//----------------------------------------------------------

	//-------------GET SEUIL------------------------
	seuil = ThingSpeak.readIntField(myChannelNumber, seuilFieldNumber, myReadAPIKey);
	//----------------------------------------------------------
	
	delay(4000);
    }
} ReadTask;

//----------------------------------------------------------------------------------------------------------------------------------------


class BlinkTask : public Task {
protected:
    void loop()  {

if(tempint>=seuil) {
      //BLINK LED
      digitalWrite(led,HIGH);
      delay(1000);
      digitalWrite(led,LOW);
      delay(1000);
    }

  delay(100);
    }
} BlinkTask;

//----------------------------------------------------------------------------------------------------------------------------------------

class SensorsTask : public Task {
protected:
    void loop()  {

 if(on_off==1){//ON
    tempint=(int) round (0.322 * analogRead(A0));
    ThingSpeak.writeField(myChannelNumber, 1, tempint, myWriteAPIKey);
 }
  delay(16000);
    }
} SensorsTask;

//-----------------------------------------------------------------------------------------------------------------------------------------------------------


void setup() {
WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
while (WiFi.status() != WL_CONNECTED) {
    delay(500);
}

 ThingSpeak.begin(client);

//--------READ TEMP SETUP--------------------
  pinMode(A0,INPUT);
//-----------------------------------------

//---------------CONFIG DIPLAY---------------------
  pinMode(D0,OUTPUT);
  pinMode(D1,OUTPUT);
  pinMode(a,OUTPUT);
  pinMode(b,OUTPUT);
  pinMode(c,OUTPUT);
  pinMode(d,OUTPUT);
  pinMode(e,OUTPUT);
  pinMode(f,OUTPUT);
  pinMode(g,OUTPUT);
 //----------------------------------------- 

 //----------CONFIG LED------
  pinMode(led,FUNCTION_3);
  pinMode(led,OUTPUT);
  //--------------------------

//---------------------------------------INIT DATA FROM CLOUD-------------------------------------------

//-------------GET ON-OFF------------------------
   on_off = ThingSpeak.readIntField(myChannelNumber, on_offFieldNumber, myReadAPIKey);
//----------------------------------------------------------

//-------------GET SEUIL------------------------
   seuil = ThingSpeak.readIntField(myChannelNumber, seuilFieldNumber, myReadAPIKey);
//----------------------------------------------------------
  
    delay(150);

    Scheduler.start(&DisplayTask);
    Scheduler.start(&ReadTask);
    Scheduler.start(&SensorsTask);
    Scheduler.start(&BlinkTask);
    Scheduler.begin();
}

void loop() {}