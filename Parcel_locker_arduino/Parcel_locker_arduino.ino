
#include <MFRC522.h>
#include <MFRC522Extended.h>
#include <require_cpp11.h>

#include <SPI.h>

#define SS_PIN 10
#define RST_PIN 9

MFRC522 mfrc522(SS_PIN, RST_PIN);




void setup() {
	// put your setup code here, to run once:
	Serial.begin(9600);
	SPI.begin();
	mfrc522.PCD_Init();

}

void loop() {
	// put your main code here, to run repeatedly:
	if (!mfrc522.PICC_IsNewCardPresent())
	{
		return;
	}
	if (!mfrc522.PICC_ReadCardSerial())
	{
		return;
	}
	String content = "";
	byte letter;
	for (byte i = 0; i < mfrc522.uid.size; i++)
	{
		//Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? "0" : " ");
		//Serial.print(mfrc522.uid.uidByte[i], HEX);
		content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? "0" : " "));
		content.concat(String(mfrc522.uid.uidByte[i], HEX));
	}

	content.toUpperCase();

	//Serial.print(content.c_str());
	for (int i = 0; i < content.length(); i++) {
		Serial.print(content[i]);
		delay(50);
	}
	delay(5000);




}
