# Esp-Ventory

### This is my C++ project from the third semester of my autumn 2024 computer science and engineering course.


## ESP32 Inventory Management System with Google Firebase

This repository contains the source code for an **IoT-based Inventory Management System** developed using the **ESP32 microcontroller**, **MFRC522 RFID module**, and **Google Firebase**. The system provides a simple and efficient way to track inventory items in real-time.

---

## Features
- **RFID Integration**: Scan and track inventory items using RFID tags for fast and reliable identification.
- **Real-time Updates**: Inventory data is updated and stored in Google Firebase for seamless synchronization.
- **CRUD Operations**: Perform Create, Read, Update, and Delete operations on inventory records.
- **User-friendly GUI**: Android app (optional) for interacting with the database to add, update, or delete inventory entries.
- **Scalability**: Suitable for small to medium-scale inventory tracking with potential for future expansion.

---

## Components
### Hardware:
- **ESP32 Microcontroller**: The core controller for managing system operations.
- **MFRC522 RFID Reader**: Used for scanning RFID tags attached to inventory items.
- **RFID Tags**: Unique identifiers for each inventory item.
- **Power Supply**: 5V or USB power source for the ESP32.

### Software:
- **Arduino IDE**: For programming the ESP32 microcontroller.
- **Firebase Realtime Database**: Cloud-based backend for storing and retrieving inventory data.
- **Android App**: Optional GUI for managing inventory (add, update, delete features).

---

## Installation
### Hardware Setup:
1. Connect the MFRC522 RFID reader to the ESP32 as per the pin configuration in the code.
2. Power the ESP32 using a 5V power source or USB.
3. Ensure the RFID tags are programmed and ready for scanning.

### Software Setup:
1. Install the **Arduino IDE** and add the ESP32 board support.
2. Install the required libraries in Arduino IDE:
   - `Firebase ESP32 Client`
   - `MFRC522`
3. Clone this repository and upload the provided code to the ESP32.
4. Set up a Firebase Realtime Database project and update the Firebase credentials in the code.

### Optional: Android App
1. Clone or download the Android app source code from the `android-app` folder.
2. Build the app in Android Studio.
3. Configure the app with your Firebase database URL and credentials.

---

## Usage
1. Power up the system and ensure the ESP32 connects to Wi-Fi.
2. Scan RFID tags using the MFRC522 reader.
3. The system will log the scanned item details in Firebase.
4. Use the Android app or Firebase console to view, update, or delete inventory records.


---

## License
This project is licensed under the [MIT License](LICENSE).



