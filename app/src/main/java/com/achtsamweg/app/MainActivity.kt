package com.achtsamweg.app

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private var pendingPdfPayload: String? = null

    // Launcher für die Android native Dateiauswahl (Speicherort für PDF)
    private val createDocumentLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri: Uri? ->
        if (uri != null && pendingPdfPayload != null) {
            try {
                val pdfBytes = createPdfFromPayload(pendingPdfPayload!!)
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(pdfBytes)
                }
                Toast.makeText(this, "PDF erfolgreich gespeichert", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Fehler beim Speichern der PDF", Toast.LENGTH_SHORT).show()
            }
        } else if (uri == null) {
            Toast.makeText(this, "Speichern abgebrochen", Toast.LENGTH_SHORT).show()
        }
        pendingPdfPayload = null
    }

    // Launcher für Notification Permission
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Erinnerungen aktiviert", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erinnerungen benötigen die Benachrichtigungs-Erlaubnis", Toast.LENGTH_LONG).show()
        }
    }

    private fun createPdfFromPayload(payload: String): ByteArray {
        val json = JSONObject(payload)
        val name = json.optString("name", "")
        val station = json.optString("station", "")
        val blocks = json.optJSONArray("blocks") ?: JSONArray()

        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 Format
        var page = document.startPage(pageInfo)
        var canvas = page.canvas

        val paintTitle = TextPaint().apply {
            textSize = 18f
            isFakeBoldText = true
            color = Color.BLACK
        }
        val paintHeader = TextPaint().apply {
            textSize = 12f
            color = Color.DKGRAY
        }
        val paintEntryTitle = TextPaint().apply {
            textSize = 12f
            isFakeBoldText = true
            color = Color.parseColor("#0d9488")
        }
        val paintEntryBody = TextPaint().apply {
            textSize = 11f
            color = Color.BLACK
        }
        val paintDateHeader = TextPaint().apply {
            textSize = 14f
            isFakeBoldText = true
            color = Color.parseColor("#334155")
        }
        val paintLine = android.graphics.Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1f
        }

        val margin = 50f
        val maxWidth = pageInfo.pageWidth - 2 * margin
        var yPos = margin

        // Helfer zum Prüfen des Seitenumbruchs
        fun checkNewPage(neededHeight: Float) {
            if (yPos + neededHeight > pageInfo.pageHeight - margin) {
                document.finishPage(page)
                page = document.startPage(pageInfo)
                canvas = page.canvas
                yPos = margin
            }
        }

        // Titel
        canvas.drawText("RE:MINDFUL - TAGEBUCH EXPORT", margin, yPos + paintTitle.textSize, paintTitle)
        yPos += paintTitle.textSize + 10f

        var headerText = "Einträge: ${blocks.length()}"
        if (name.isNotEmpty()) headerText = "Name: $name | $headerText"
        if (station.isNotEmpty()) headerText = "$headerText | Station: $station"

        canvas.drawText(headerText, margin, yPos + paintHeader.textSize, paintHeader)
        yPos += paintHeader.textSize + 30f

        // Einträge zeichnen
        for (i in 0 until blocks.length()) {
            val block = blocks.getJSONObject(i)
            val title = block.optString("title", "")
            val colorStr = block.optString("color", "#000000")
            val isHeader = block.optBoolean("isHeader", false)
            val lines = block.optJSONArray("lines") ?: JSONArray()

            if (isHeader) {
                yPos += 15f // Extra Abstand vor einem neuen Tag
                checkNewPage(paintDateHeader.textSize + 15f)
                
                canvas.drawText("📅 $title", margin, yPos + paintDateHeader.textSize, paintDateHeader)
                yPos += paintDateHeader.textSize + 8f
                
                // Eine dünne Trennlinie zeichnen
                canvas.drawLine(margin, yPos, margin + maxWidth, yPos, paintLine)
                yPos += 12f
                continue
            }

            paintEntryTitle.color = try { Color.parseColor(colorStr) } catch(e: Exception) { Color.BLACK }

            // Title zeichnen
            val titleLayout = StaticLayout.Builder.obtain(title, 0, title.length, paintEntryTitle, maxWidth.toInt())
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()

            checkNewPage(titleLayout.height + 10f)

            canvas.save()
            canvas.translate(margin, yPos)
            titleLayout.draw(canvas)
            canvas.restore()
            yPos += titleLayout.height + 6f

            // Inhalt zeichnen
            for (j in 0 until lines.length()) {
                val lineText = lines.optString(j, "")
                if (lineText.isEmpty()) continue

                val lineLayout = StaticLayout.Builder.obtain(lineText, 0, lineText.length, paintEntryBody, maxWidth.toInt())
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(0f, 1.2f)
                    .setIncludePad(false)
                    .build()

                checkNewPage(lineLayout.height + 6f)

                canvas.save()
                canvas.translate(margin + 10f, yPos) // Leicht eingerückt
                lineLayout.draw(canvas)
                canvas.restore()
                yPos += lineLayout.height + 4f
            }

            yPos += 15f // Abstand zum nächsten Eintrag
        }

        document.finishPage(page)

        val outputStream = ByteArrayOutputStream()
        document.writeTo(outputStream)
        document.close()
        return outputStream.toByteArray()
    }

    private fun scheduleSystemNotification(enabled: Boolean, time: String, note: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java).apply {
            putExtra("note", note)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (!enabled) {
            // Alarm stornieren
            alarmManager.cancel(pendingIntent)
            return
        }

        // Benachrichtigungsberechtigung anfragen (ab Android 13)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Zeit berechnen
        val timeParts = time.split(":")
        if (timeParts.size != 2) return
        val hour = timeParts[0].toIntOrNull() ?: 19
        val min = timeParts[1].toIntOrNull() ?: 0

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
        }

        // Wenn die Zeit für heute schon in der Vergangenheit liegt, auf morgen setzen
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Sich wiederholenden Alarm setzen (täglich)
        try {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } catch (e: SecurityException) {
            // Fallback falls SCHEDULE_EXACT_ALARM fehlt
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun generateAndSavePdf(payload: String, filename: String) {
            pendingPdfPayload = payload
            runOnUiThread {
                createDocumentLauncher.launch(filename)
            }
        }

        @JavascriptInterface
        fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
        
        @JavascriptInterface
        fun scheduleNotification(enabled: Boolean, time: String, note: String) {
            runOnUiThread {
                scheduleSystemNotification(enabled, time, note)
            }
        }
        
        @JavascriptInterface
        fun triggerTestNotification(note: String) {
            runOnUiThread {
                val intent = Intent(this@MainActivity, ReminderReceiver::class.java).apply {
                    putExtra("note", note)
                }
                sendBroadcast(intent)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Statusleiste ausblenden (Vollbild)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        val webSettings: WebSettings = webView.settings

        // Web-Einstellungen & LocalStorage aktivieren
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        @Suppress("DEPRECATION")
        webSettings.databaseEnabled = true
        webSettings.allowFileAccess = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        webView.webViewClient = WebViewClient()
        
        // Javascript-Interface hinzufügen, damit die WebApp mit Android kommunizieren kann
        webView.addJavascriptInterface(WebAppInterface(), "AndroidApp")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        // Lädt die index.html sicher als HTML-String
        try {
            val inputStream = assets.open("index.html")
            val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))
            val htmlContent = reader.readText()
            reader.close()
            inputStream.close()

            webView.loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "UTF-8", null)
        } catch (e: Exception) {
            e.printStackTrace()
            webView.loadUrl("file:///android_asset/index.html")
        }
    }
}
