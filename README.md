# Re:Mindful - Klinik- & Befindlichkeitsbegleiter / Clinic & Wellness Companion

![Re:Mindful App Icon](app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp) <!-- Du kannst hier später einen echten Bildlink einfügen -->

*🌍 For English description, please scroll down.*

**Re:Mindful** ist ein sicherer, privater und offline-first Begleiter für Achtsamkeit, Mood-Tracking und das Dokumentieren persönlicher Erfolge. Die App wurde speziell entwickelt, um Menschen während eines Klinikaufenthalts, einer Therapie oder im achtsamen Alltag zu unterstützen.

## ✨ Features (Deutsch)

* **😊 Mood-Tracker:** Halte täglich fest, wie du dich fühlst und reflektiere, was positiv oder belastend war.
* **🎯 Ziel-Reflexion & Smart-Assistent:** Setze dir konkrete Tages- oder Wochenziele. Der therapeutische KI-Assistent hilft dir komplett offline, große Ziele in machbare, kleine Mikroschritte zu zerlegen.
* **🏆 Erfolgstagebuch:** Ein geschützter Raum nur für positive Momente. Misserfolge haben hier Pause. Feiere deine großen und kleinen Schritte mit einem Klick!
* **📖 Integriertes Tagebuch:** Alle Einträge werden chronologisch und übersichtlich gespeichert.
* **📄 PDF-Export:** Erstelle mit einem Klick ein strukturiertes PDF deiner Einträge (gefiltert nach Datum). Ideal, um es in die nächste Therapiestunde mitzubringen oder in Akten abzulegen.
* **🔔 Streaks & Erinnerungen:** Die App erinnert dich (auf Wunsch) über lokale Push-Benachrichtigungen an deinen täglichen Eintrag und feiert deine Regelmäßigkeit ("Streaks") mit dir.
* **✏️ Volle Kontrolle:** Einträge können jederzeit korrigiert oder gelöscht werden. Die App merkt sich deine Sprache und deinen Namen über ein kurzes Onboarding. Alle Daten können in den Einstellungen auf Knopfdruck gelöscht werden.
* **🌐 Zweisprachig:** Die App ist komplett in Deutsch und Englisch verfügbar.

## 🔒 100% Datenschutz (Privacy-by-Design)

Gesundheits- und Befindlichkeitsdaten sind hochsensibel. Re:Mindful ist darauf ausgelegt, deine Privatsphäre absolut zu schützen:
* **Offline-First:** Die App fordert keine Internetberechtigung vom System an.
* **Lokale Speicherung:** Alle Daten verbleiben auf deinem eigenen Gerät (`localStorage`).
* **Keine Cloud, Keine Tracker:** Wir speichern nichts auf Servern, nutzen keine versteckten Analyse-Tools und teilen keine Daten mit Dritten.
* Der Smart-Assistent funktioniert durch ein lokales Regelwerk (NLP), es findet kein Austausch mit OpenAI oder Google-Servern statt.

---

# Re:Mindful - Clinic & Wellness Companion

**Re:Mindful** is a secure, private, and offline-first companion for mindfulness, mood tracking, and documenting personal wins. The app was specifically designed to support people during hospital stays, therapy, or simply in mindful daily life.

## ✨ Features (English)

* **😊 Mood Tracker:** Record how you feel every day and reflect on what was positive or stressful.
* **🎯 Goal Reflection & Smart Assistant:** Set concrete daily or weekly goals. The therapeutic smart assistant helps you break down large goals into actionable, tiny micro-steps—completely offline.
* **🏆 Success Diary:** A safe space exclusively for positive moments. Failures take a break here. Celebrate your big and small steps with just a tap!
* **📖 Integrated Diary:** All entries are stored chronologically and neatly in one place.
* **📄 PDF Export:** Create a structured PDF of your entries (filtered by date) with one click. Perfect for bringing to your next therapy session.
* **🔔 Streaks & Reminders:** The app sends you local push notifications to remind you to log your day and celebrates your consistency ("Streaks") with you.
* **✏️ Full Control:** Entries can be edited or deleted at any time. The app remembers your preferred language and name through a quick onboarding. You can irreversibly wipe all data in the settings.
* **🌐 Bilingual:** The app is fully available in English and German.

## 🔒 100% Privacy (Privacy-by-Design)

Health and mood data are highly sensitive. Re:Mindful is built to absolutely protect your privacy:
* **Offline-First:** The app does not request internet permissions from your operating system.
* **Local Storage:** All data stays on your own device (`localStorage`).
* **No Cloud, No Trackers:** We don't store anything on servers, use no hidden analytics tools, and don't share data with third parties.
* The smart assistant works through a local rule engine (NLP); there is no data exchange with OpenAI or Google servers.

## 🛠️ Technologie / Technology

Re:Mindful uses a modern hybrid approach for maximum speed and flexibility:
* **Android / Kotlin (Wrapper):** `WebView` integration, native PDF generation (Android `PdfDocument`), File Provider, and local AlarmManager for push notifications.
* **Frontend:** Vanilla HTML5, CSS3, and JavaScript, embedded as a local asset (`assets/index.html`).

## 🚀 Installation für Entwickler / Installation for Developers

Um das Projekt lokal in Android Studio auszuführen / To run the project locally in Android Studio:

1. Klone dieses Repository / Clone this repository:
   ```bash
   git clone https://github.com/tisvie/remindful-android.git
   ```
2. Öffne den Ordner in **Android Studio** / Open the folder in Android Studio.
3. Klicke auf **Run** (Grüner Play-Button) / Click **Run** (Green Play button).

## 📜 Lizenz / License
This project is licensed under the [MIT License](LICENSE).
