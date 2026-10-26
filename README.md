# Re:Mindful - Klinik- & Befindlichkeitsbegleiter

![Re:Mindful App Icon](https://github.com/tsvie/ReMindful/blob/main/icon.png) <!-- Du kannst hier später einen echten Bildlink einfügen -->

**Re:Mindful** ist ein sicherer, privater und offline-first Begleiter für Achtsamkeit, Mood-Tracking und das Dokumentieren persönlicher Erfolge. Die App wurde speziell entwickelt, um Menschen während eines Klinikaufenthalts, einer Therapie oder im achtsamen Alltag zu unterstützen.

## ✨ Features

* **😊 Mood-Tracker:** Halte täglich fest, wie du dich fühlst und reflektiere, was positiv oder belastend war.
* **🎯 Ziel-Reflexion:** Setze dir konkrete Tages- oder Wochenziele (inklusive Schnelloptionen) und werte diese am Abend aus.
* **🏆 Erfolgstagebuch:** Ein geschützter Raum nur für positive Momente. Misserfolge haben hier Pause. Feiere deine großen und kleinen Schritte mit einem Klick!
* **📖 Integriertes Tagebuch:** Alle Einträge werden chronologisch und übersichtlich gespeichert.
* **📄 PDF-Export:** Erstelle mit einem Klick ein strukturiertes PDF deiner Einträge (gefiltert nach Datum). Ideal, um es in die nächste Therapiestunde mitzubringen oder in Akten abzulegen.
* **🔔 Streaks & Erinnerungen:** Die App erinnert dich (auf Wunsch) über lokale Push-Benachrichtigungen an deinen täglichen Eintrag und feiert deine Regelmäßigkeit ("Streaks") mit dir.
* **✏️ Bearbeiten & Löschen:** Einträge können jederzeit korrigiert oder gelöscht werden (mit Sicherheitsabfrage).

## 🔒 100% Datenschutz (Privacy-by-Design)

Gesundheits- und Befindlichkeitsdaten sind hochsensibel. Re:Mindful ist darauf ausgelegt, deine Privatsphäre absolut zu schützen:
* **Offline-First:** Die App fordert keine Internetberechtigung vom System an.
* **Lokale Speicherung:** Alle Daten verbleiben auf deinem eigenen Gerät (`localStorage`).
* **Keine Cloud, Keine Tracker:** Wir speichern nichts auf Servern, nutzen keine versteckten Analyse-Tools und teilen keine Daten mit Dritten.
* PDF-Exporte geschehen lokal und werden nur versendet, wenn du (der User) die PDF-Datei eigenhändig über eine andere App (z.B. Mail) verschickst.

## 🛠️ Technologie

Re:Mindful nutzt einen modernen Hybrid-Ansatz für maximale Geschwindigkeit und Flexibilität:
* **Android / Kotlin (Wrapper):** `WebView` Integration, Native PDF-Erstellung (Android `PdfDocument`), File Provider und lokaler AlarmManager für Push-Benachrichtigungen.
* **Frontend:** Vanilla HTML5, CSS3 und JavaScript, eingebettet als lokales Asset (`assets/index.html`).

## 🚀 Installation für Entwickler

Um das Projekt lokal in Android Studio auszuführen:

1. Klone dieses Repository:
   ```bash
   git clone https://github.com/DEIN_USERNAME/remindful-android.git
   ```
2. Öffne den Ordner in **Android Studio**.
3. Warte, bis Gradle die Abhängigkeiten synchronisiert hat.
4. Klicke auf **Run** (Grüner Play-Button), um die App auf einem Emulator oder verbundenen Android-Gerät zu testen.

## 📜 Lizenz

Dieses Projekt ist lizenziert unter der [MIT License](LICENSE). 
*(Du kannst die Lizenz je nach deinen Vorlieben anpassen).*
