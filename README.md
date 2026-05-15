# Sahyadri-Samrakshane (Forest Sentinel) 🌲🛡️

**Sahyadri-Samrakshane** is a robust, offline-first mobile application designed for citizens and forest rangers to protect the Sahyadri mountain range (Western Ghats). It enables real-time reporting of forest incidents like illegal logging, poaching, and forest fires, even in remote areas with limited connectivity.

## 🚀 Vision
To empower local communities with modern tools to safeguard one of the world's most significant biodiversity hotspots through community-driven monitoring and rapid response coordination.

## ✨ Key Features
- **Smart Authentication**: Secure login via Firebase Authentication.
- **Precision Reporting**: 
  - **CameraX Integration**: High-performance camera interface for capturing incident evidence.
  - **GPS Tracking**: Automatic high-accuracy coordinate attachment using FusedLocationProviderClient.
- **Offline-First Resilience**:
  - **Local Storage**: Reports are saved locally using Room Database if the network is unavailable.
  - **Background Sync**: Automatic data synchronization via WorkManager when connectivity returns.
- **Real-Time Monitoring**:
  - **Alert Dashboard**: Detailed list of submitted reports.
  - **Status Timeline**: Visual progress tracking from "Reported" to "Team Dispatched".
- **Forest Branding UI**: Custom Material 3 theme incorporating forest tones (Forest Green, Earth Brown).

## 🛠️ Architecture
The app follows **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** pattern:
- **Presentation Layer**: Jetpack Compose for a modern, reactive UI.
- **Domain Layer**: Pure Kotlin business logic with UseCases and Repository interfaces.
- **Data Layer**: Concrete implementations of repositories, local Room DB, and Firebase remote sources.
- **Dependency Injection**: Hilt for robust and testable DI.

## 🧰 Tech Stack
- **Languages**: Kotlin, TypeScript (Server)
- **Framework**: Jetpack Compose
- **Database**: Room (Local), Firebase Firestore (Remote)
- **Files**: Firebase Storage (Images)
- **Background Task**: Android WorkManager
- **Networking**: Kotlin Coroutines & Flow
- **Maps**: Google Maps SDK for Android (Compose)
- **Image Loading**: Coil

## 📥 Getting Started
This repository contains a full-stack project architecture. 
- The **Android** source code is located in the `app/` directory.
- The **Development Server** (Vite + Express) handles deployment metadata and local preview.

### Exporting to Android Studio
1. Download the ZIP file via the **Settings (Gear Icon) -> Export to ZIP** menu in AI Studio.
2. Extract and open the project root in Android Studio Hedgehog or newer.
3. Ensure you have the `google-services.json` file placed in the `app/` folder (configure your own Firebase project for production use).

## 📄 License
This project is part of the Sahyadri Sentinel Initiative. 
*"Protect the mountains, and they will protect you."*
