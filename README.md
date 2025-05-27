# 📱 Smartphone GPS-Based Speedometer with Speed Limit Alerts

This Android app tracks vehicle speed in real-time using GPS and accelerometer sensors, and provides speed limit alerts to promote safe driving behavior. Built as a final project for INFS 611: Rapid Information Systems Prototyping (Spring 2024), George Mason University.

## 🧠 Features

- Real-time speed display in km/h and mph
- Speed limit alerts with dialog popups
- Personalized UI greeting
- Dynamic sensor data (GPS + accelerometer)
- Visual feedback and simulation using Android Emulator
- Map-based testing with simulated GPX/KML routes

## 🛠 Tech Stack

- **Frontend**: Android Studio (Java, XML UI)
- **Sensors**: GPS, Accelerometer
- **Tools**: Android Emulator, Route Simulation, GPX/KML Testing

## 📱 Screenshots

Include screenshots like:
- Home screen with name entry
- Real-time speed display
- Speed alert popup
- Map simulator with route testing

## 🧱 System Architecture

- **MainActivity**: Handles user entry and navigation
- **MainActivity2**: Tracks speed using `onLocationChanged()`
- **AlertDialog**: Warns users on speed limit violations
- **Switch Toggle**: Convert between km/h and mph
- **Accelerometer Integration**: Detects sudden stops and accelerations

## 📽️ Demo

- In-app simulation using Android emulator
- Map route testing with speed alerts
- [Optionally link a YouTube video or demo folder]

## 👨‍🎓 Course

**INFS 611 – Rapid Information Systems Prototyping**  
George Mason University, Spring 2024  
**Group 4 Members**:  
- Mani Kumar Edukoju  
- Sai Amartya Maruth Mandedi  
- Ashrith Bhooka Ravinandan  
- Jinyang Li  
- Vignesh Thangellapally  

---

## 💡 Future Improvements

- Integration with real-world traffic data for dynamic speed limits  
- Machine learning to profile driving habits over time  
- Backend support for trip logging and analytics  

---

## 🏁 Conclusion

This app showcases how smartphone sensors can improve driving safety through real-time alerts and behavior tracking. It’s designed with scalability and adaptability in mind for future transportation applications.
