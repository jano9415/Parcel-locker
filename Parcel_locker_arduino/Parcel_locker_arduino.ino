
#include <MFRC522.h>
#include <MFRC522Extended.h>
#include <require_cpp11.h>

#include <SPI.h>

#define SS_PIN 10
#define RST_PIN 9

MFRC522 mfrc522(SS_PIN, RST_PIN);

//Rekeszek
const int box1 = 2;
const int box2 = 3;
const int box3 = 4;
const int box11 = 6;
const int box21 = 7;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  pinMode(box1, OUTPUT);
  pinMode(box2, OUTPUT);
  pinMode(box3, OUTPUT);
  pinMode(box11, OUTPUT);
  pinMode(box21, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(box1, LOW);
  digitalWrite(box2, LOW);
  digitalWrite(box3, LOW);
  digitalWrite(box11, LOW);
  digitalWrite(box21, LOW);

  //Soros port olvasás
  //Az adat az angular alkalmazásból jön
  //Rekesz kinyitása ha az ügyfél átveszi a csomagját vagy a futár feltölti vagy kiüríti az automatát
  if (Serial.available() > 0) {

    char receivedChar = Serial.read();

    switch (receivedChar) {
      case '1':
        digitalWrite(box1, HIGH);
        delay(500);
        digitalWrite(box1, LOW);
        break;
      case '2':
        digitalWrite(box2, HIGH);
        delay(500);
        digitalWrite(box2, LOW);
        break;
      case '3':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '4':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '5':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '6':
        digitalWrite(box1, HIGH);
        delay(500);
        digitalWrite(box1, LOW);
        break;
      case '7':
        digitalWrite(box2, HIGH);
        delay(500);
        digitalWrite(box2, LOW);
        break;
      case '8':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '9':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '10':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '11':
        digitalWrite(box1, HIGH);
        delay(500);
        digitalWrite(box1, LOW);
        break;
      case '12':
        digitalWrite(box2, HIGH);
        delay(500);
        digitalWrite(box2, LOW);
        break;
      case '13':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '14':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '15':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '16':
        digitalWrite(box1, HIGH);
        delay(500);
        digitalWrite(box1, LOW);
        break;
      case '17':
        digitalWrite(box2, HIGH);
        delay(500);
        digitalWrite(box2, LOW);
        break;
      case '18':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '19':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '20':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '21':
        digitalWrite(box1, HIGH);
        delay(500);
        digitalWrite(box1, LOW);
        break;
      case '22':
        digitalWrite(box2, HIGH);
        delay(500);
        digitalWrite(box2, LOW);
        break;
      case '23':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '24':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '25':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '26':
        digitalWrite(box1, HIGH);
        delay(500);
        digitalWrite(box1, LOW);
        break;
      case '27':
        digitalWrite(box2, HIGH);
        delay(500);
        digitalWrite(box2, LOW);
        break;
      case '28':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '29':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;
      case '30':
        digitalWrite(box3, HIGH);
        delay(500);
        digitalWrite(box3, LOW);
        break;


      default:
        Serial.print("Error");
    }
  }

  if (!mfrc522.PICC_IsNewCardPresent()) {
    return;
  }
  if (!mfrc522.PICC_ReadCardSerial()) {
    return;
  }

  //Soros adatküldés az angular alkalmazásnak
  //Rfid kártya olvasása
  String content = "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) {
    //Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? "0" : " ");
    //Serial.print(mfrc522.uid.uidByte[i], HEX);
    content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? "0" : " "));
    content.concat(String(mfrc522.uid.uidByte[i], HEX));
    digitalWrite(2, HIGH);
  }

  content.toUpperCase();

  //Serial.print(content.c_str());
  for (int i = 0; i < content.length(); i++) {
    Serial.print(content[i]);
    delay(50);
  }
  delay(2000);
}
