# Product Requirements Document: Sahyadri-Samrakshane (Sahyadri Sentinel)

## 1. Executive Summary
**Sahyadri-Samrakshane** is a mobile application designed to empower citizens and field agents to protect the environmental integrity of the Sahyadri (Western Ghats) region. The app provides a streamlined interface for reporting environmental threats, such as illegal logging, poaching, or forest fires, by capturing geo-tagged images and detailed descriptions in real-time.

## 2. Project Goals
- **Real-time Monitoring:** Provide a platform for immediate reporting of environmental incidents.
- **Data Integrity:** Ensure reports are geo-tagged and timestamped to provide verifiable data to authorities.
- **User Engagement:** Build a community of "Sentinels" who actively participate in ecological conservation.

## 3. Target Audience
- **Local Communities:** Residents living in or near forest fringes.
- **Environmental NGOs:** Field workers monitoring ecological health.
- **Forest Department Officials:** For receiving and validating field reports.

## 4. Key Features

### 4.1. Authentication & User Management
- **Multi-method Sign-in:** Support for Email/Password and Google Sign-In.
- **Comprehensive Profiles:** Collection of full name and phone number during registration for accountability.
- **Hassle-free Logout:** Secure exit from the application.

### 4.2. Incident Reporting (The "Sentinel" Core)
- **Categorization:** Users can select from predefined categories (e.g., Fire, Illegal Encroachment, Wildlife Incident).
- **Location Intelligence:**
    - Automated GPS/Network-based location fetching.
    - Mandatory location permission checks.
    - Proactive prompts for users to enable Location Services if disabled.
- **Visual Evidence:** Integrated camera functionality to capture images directly from the field.
- **Detailed Descriptions:** Text input to provide context to the visual evidence.

### 4.3. UI/UX & Feedback
- **Modern Design:** Built with Jetpack Compose and Material 3 for a fluid experience.
- **Theme Customization:** Support for System, light, and dark modes to ensure readability in various field conditions.
- **Interactive States:** Loading indicators and success/error feedback for all network operations.

## 5. Technology Stack
- **Platform:** Android (min SDK 24+)
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture with MVVM (Model-View-ViewModel)
- **Dependency Injection:** Dagger Hilt
- **Backend Services:**
    - **Authentication:** Firebase Auth
    - **Database:** Google Cloud Firestore (Primary storage for reports and user metadata)
- **Imaging:** CameraX / Activity Result APIs
- **Location:** Google Play Services Location API

## 6. Functional Requirements
- **FR1:** The system shall prevent report submission if the device location is not determined.
- **FR2:** The system shall automatically merge user data into Firestore upon Google Sign-In.
- **FR3:** Images captured via the report screen must be associated with the specific report document in the database (via UUID).
- **FR4:** The application must persist theme preferences across sessions.

## 7. Non-Functional Requirements
- **Scalability:** Must handle concurrent reporting from multiple geographical zones.
- **Performance:** App launch and navigation between Home and Report screens should be sub-1 second.
- **Security:** Firestore Security Rules must ensure that users can only modify their own reports while authorities can read all reports.
