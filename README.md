
# 📱 Vitesse – Candidate Management App

_Vitesse_ is an Android application built using Jetpack Compose, designed to streamline candidate management within a recruitment context. Developed as part of a professional career transition into mobile development, this project reflects real-world features, a clean MVVM architecture, and thoughtful UI/UX principles.

## 🚀 About the Project

As a fictional HR platform, Vitesse allows recruiters to:
- View a list of applicants
- Search and filter candidates
- Add new profiles
- Edit existing applicant data
- See detailed information about each candidate, including contact and salary info
- Mark favorites
- Persist photos and rich applicant notes
- Convert salary values using live exchange rates (EUR/GBP)

The app was built entirely in Kotlin using modern Android best practices.

## 👨‍💼 Author

_Olivier Marteaux_  
Former aerospace engineer turned Android developer.

Read more about my transition on [LinkedIn](https://linkedin.com/in/olivier-marteaux).  
Check out my journey and projects:
- 🔗 [Google Developer Profile](https://g.dev/OlivierMarteaux)
- 💻 [GitHub Projects](https://github.com/OlivierMarteaux)
- 📢 [LinkedIn Post – Career Change](https://www.linkedin.com/posts/olivier-marteaux_androidbasics-careerchange-androiddevelopment-activity-7351370158369628164-FmqZ?utm_source=share&utm_medium=member_desktop&rcm=ACoAACynrz8BkrhJFrStq3CEX6rQIEfnG7goFdg)

## 🧰 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM, ViewModel, State Management |
| Navigation | Jetpack Navigation Compose |
| Data Storage | Room Database |
| Background Work | Kotlin Coroutines, Flows |
| Image Handling | URI-based storage in internal memory |
| Permissions | Runtime management (calls, storage) |
| Logging | Custom logger with debug mode |
| Build | Gradle (KTS) |

## 🧪 Features

✅ Add, edit, and delete candidate profiles  
✅ Attach photos from device gallery  
✅ Favorite/unfavorite candidates  
✅ Search bar with live filtering  
✅ Live salary conversion using API  
✅ Alert and confirmation dialogs  
✅ Persistent state handling  
✅ Modern Material 3 design  
✅ Full offline access to saved data

## 📸 Screenshots

Visit the following link to browse screenshots of the Vitesse application:  
🔗 [Vitesse App Screenshots](screenshots/)

## 📲 Install

To install the Vitesse application on your physical Android device:

1. **Download the APK**
   - Go to the [Releases](https://github.com/your-repo/vitesse/releases) section of this repository.
   - Download the latest `vitesse-release.apk` file.

2. **Transfer the APK to your device**
   - Connect your device to your computer using a USB cable.
   - Copy the APK file to a folder on your device (e.g., `Downloads`).

3. **Enable Unknown Sources**
   - On your device, go to `Settings` > `Security`.
   - Enable **Install from unknown sources** (you can disable it again after installation).

4. **Install the APK**
   - Use a file explorer app on your device to locate the APK file.
   - Tap on it and follow the prompts to install the app.

5. **Launch the App**
   - Once installed, open the app from your launcher and start using Vitesse!

> ⚠️ Note: You may need to allow permissions during the first launch.

## 📂 Project Structure

```
Vitesse/
├── ui/                   # Composables & UI screens
├── viewmodel/            # ViewModel layer
├── data/
│   ├── model/            # Data models (Applicant, ExchangeRate, etc.)
│   ├── local/            # Room DB + DAO
│   └── repository/       # Abstractions + implementation
├── utils/                # Logger, extensions, permission utils
├── navigation/           # Destinations and NavHost
└── VitesseApplication.kt # App-level container & DI
```

## ⚙️ Setup

1. Clone the repo:
   ```bash
   git clone https://github.com/oliviermarteaux/Vitesse.git
   ```

2. Open in **Android Studio**

3. Configure API Key (if needed for exchange rate API)

4. Run on an emulator or physical device (API 26+ recommended)

## 🔐 Permissions

This app requests the following permissions at runtime:
- 📞 Call phone numbers
- 📸 Camera (if extended in future)

## 📈 Current Limitations

- No backend sync yet — works fully offline  
- Light theme only (dark mode coming)

## 🧭 Roadmap (if extended in future)

- 🔲 Add login/authentication layer  
- 🔲 Export applicant list as PDF  
- 🔲 Multi-language support improvements  
- 🔲 Integrate cloud sync with Firebase or Supabase

## 🤝 Acknowledgments

- [OpenClassrooms Android Pathway](https://openclassrooms.com/fr/paths/527/projects/1640/364-mission---option-b---cas-fictif)
- [Google Android Basics](https://developer.android.com/courses/android-basics-compose/course)
- JetBrains & Jetpack Compose Community

## 📄 License

This project is for educational and demonstration purposes. Not licensed for commercial use. For inquiries, please contact me.

---

_“From Space to Code” – a journey of reinvention and curiosity._