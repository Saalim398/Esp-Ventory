/*
inventory management system using esp32 and mfrc522 rfid

*/
#include <SPI.h>
#include <WiFi.h>
#include <MFRC522.h>
#include <Firebase_ESP_Client.h>
#include <addons/TokenHelper.h>
#include <addons/RTDBHelper.h>

// Pins for RFID
#define SS_PIN 21  // ESP32 pin GPIO21
#define RST_PIN 22 // ESP32 pin GPIO22

// Wi-Fi credentials
#define WIFI_SSID "your-wifi-ssid"
#define WIFI_PASSWORD "your-wifi-password"

// Firebase configuration
#define API_KEY "your-firebase-api-key"
#define DB_URL "your-firebase-database-url"

// Firebase email and password
#define USER_EMAIL "firebase-authentication-email"
#define USER_PASSWORD "firebase-authenticaton-password"

// Create instances for RFID and Firebase
MFRC522 mfrc522(SS_PIN, RST_PIN);
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

unsigned long sendDataPrevMillis = 0;
int isSignedIn = 0;

void setup()
{
    // Serial monitor setup
    Serial.begin(115200);
    SPI.begin();
    mfrc522.PCD_Init();
    Serial.println("RFID reader initialized");

    // Wi-Fi setup
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.println("Connecting to Wi-Fi...");
    while (WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(300);
    }
    Serial.println("\nWi-Fi connected!");
    Serial.print("IP Address: ");
    Serial.println(WiFi.localIP());

    // Firebase setup
    config.api_key = API_KEY;
    config.database_url = DB_URL;
    auth.user.email = USER_EMAIL;
    auth.user.password = USER_PASSWORD;
    config.token_status_callback = tokenStatusCallback;
    Firebase.begin(&config, &auth);
}

void loop()
{
    if (Firebase.ready() && (millis() - sendDataPrevMillis > 15000 || sendDataPrevMillis == 0))
    {
        sendDataPrevMillis = millis();

        // Look for new RFID tags
        if (mfrc522.PICC_IsNewCardPresent())
        {
            if (mfrc522.PICC_ReadCardSerial())
            {
                String rfid = "";
                for (byte i = 0; i < mfrc522.uid.size; i++)
                {
                    rfid += String(mfrc522.uid.uidByte[i], HEX);
                }
                Serial.println("RFID Tag Read: " + rfid);

                // Update Firebase inventory
                String path = "/inventory/" + rfid;
                String qpath = "/inventory";
                int quantity = 0;

                if (Firebase.RTDB.setString(&fbdo, path, "in stock"))
                {

                    if (Firebase.RTDB.getInt(&fbdo, qpath + "/quantity"))
                    {
                        quantity = fbdo.intData();
                        Serial.println("Current quantity: " + String(quantity));

                        if (quantity > 0)
                        {
                            quantity--;

                            if (Firebase.RTDB.setInt(&fbdo, path + "/quantity", quantity))
                            {
                                Serial.println("Inventory updated in Firebase! New quantity: " + String(quantity));
                            }
                            else
                            {
                                Serial.println("Error updating Firebase: " + fbdo.errorReason());
                            }
                        }
                        else
                        {
                            Serial.println("Quantity is already 0, cannot decrement.");
                        }
                    }
                    else
                    {
                        Serial.println("Error reading from Firebase: " + fbdo.errorReason());
                    }
                }
                else
                {
                    Serial.println("Error setting inventory status in Firebase: " + fbdo.errorReason());
                }

                mfrc522.PICC_HaltA();
            }
        }
    }
}
