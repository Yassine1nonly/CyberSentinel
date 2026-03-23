# CyberSentinel – Android App (SOC Monitor)

A native Android **Mini-SIEM mobile client** for real-time SOC alert monitoring.

---

## 📱 Features

| Feature | Description |
|---|---|
| 🔐 Login Screen | Secure analyst authentication |
| 📊 Dashboard | Live threat level, pie chart, 24h metrics |
| 🚨 Alerts List | Filter by Critical / High / Medium / Low |
| 📄 Alert Detail | Full incident info, MITRE ATT&CK, evidence |
| ✅ Acknowledge / Resolve | One-tap alert status update |
| 🌐 Suspicious IPs | Ranked list of malicious source IPs |
| 🔔 Push Notifications | FCM alerts by severity level |
| ⚙️ Settings | Backend URL config, notification toggles |

---

## 🏗️ Architecture

```
android/
├── app/src/main/
│   ├── java/com/socmonitor/
│   │   ├── model/          # Alert, DashboardStats
│   │   ├── network/        # MockDataRepository, FCMService
│   │   ├── ui/
│   │   │   ├── activities/ # Splash, Login, Main, AlertDetail
│   │   │   ├── fragments/  # Dashboard, Alerts, IPs, Settings
│   │   │   └── adapters/   # AlertsAdapter, IpAdapter
│   │   └── utils/          # SessionManager, SeverityUtils
│   └── res/
│       ├── layout/         # All XML layouts
│       ├── drawable/       # Vector icons
│       ├── menu/           # Navigation menus
│       └── values/         # Colors, strings, themes
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK 34
- JDK 8+
- A Firebase project (for push notifications)

### Setup

1. **Open in Android Studio**
   ```
   File → Open → select the `android/` folder
   ```

2. **Sync Gradle**
   Android Studio will auto-prompt. Click *Sync Now*.

3. **Firebase (optional for push notifications)**
   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create a project → Add Android app (`com.socmonitor`)
   - Download `google-services.json` → place in `android/app/`
   - Add to `android/build.gradle`:
     ```groovy
     id 'com.google.gms.google-services' version '4.4.0' apply false
     ```
   - Add to `android/app/build.gradle`:
     ```groovy
     id 'com.google.gms.google-services'
     ```

4. **Run the app**
   - Connect a device or start an emulator (API 26+)
   - Press ▶ Run

### Demo Login
```
Email:    analyst@soc.local
Password: soc@2024
```

---

## 🔌 Connecting to Real Backend

The app currently uses `MockDataRepository` with realistic sample data.

To connect to the real Python backend:

1. Open `MockDataRepository.java`
2. Replace mock methods with Retrofit calls to your API:
   ```
   GET  /api/alerts
   GET  /api/alerts/{id}
   PUT  /api/alerts/{id}/acknowledge
   PUT  /api/alerts/{id}/resolve
   GET  /api/dashboard/stats
   ```

3. Update the backend URL in the app's **Settings** screen.

The backend is in the `/app` folder of this repo (Python/FastAPI).

---

## 🛡️ Tech Stack

- **Language**: Java
- **UI**: Material Design 3, ViewBinding
- **Charts**: MPAndroidChart
- **Networking**: Retrofit 2 + OkHttp
- **Architecture**: Fragment + ViewModel + LiveData
- **Notifications**: Firebase Cloud Messaging (FCM)
- **Navigation**: Bottom Navigation + Fragment Manager

---

## 📸 Screens

| Splash | Login | Dashboard | Alerts | Alert Detail | Settings |
|--------|-------|-----------|--------|--------------|----------|
| Shield logo | Auth form | Threat level + PieChart | Filtered list | Full incident | Notification config |

---

## 👨‍💻 Project Context

Built as MVP for the **Uberisation d'un service** academic project.
Service chosen: **SOC Alert Monitoring Platform** (Mini-SIEM mobile).
