package com.achtsamweg.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val channelId = "achtsamweg_reminders"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Channel erstellen (für Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Tägliche Erinnerung",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Erinnert dich an deinen Tagebucheintrag"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent zum Öffnen der App beim Klick auf die Benachrichtigung
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Nachricht aus dem Intent auslesen (oder Standard-Text)
        val noteText = intent.getStringExtra("note") ?: "Zeit für deinen Tagebucheintrag."

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // Ggf. eigenes kleines Icon verwenden
            .setContentTitle("AchtsamWeg")
            .setContentText(noteText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
        
        // Da AlarmManager nicht immer verlässlich wiederholt, wenn Gerät neu startet,
        // könnte man hier den Alarm für den nächsten Tag neu setzen, 
        // oder setRepeating in der MainActivity verwenden.
    }
}
