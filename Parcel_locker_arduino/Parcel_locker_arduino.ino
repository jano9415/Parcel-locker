
#include <MFRC522.h>
#include <MFRC522Extended.h>
#include <require_cpp11.h>

#include <SPI.h>

#define SS_PIN 10
#define RST_PIN 9

MFRC522 mfrc522(SS_PIN, RST_PIN);

//Rekeszek
const int box1 = 2;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  pinMode(box1, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(2, LOW);

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
        digitalWrite(box1, HIGH);
        delay(2000);
        digitalWrite(box1, LOW);
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
