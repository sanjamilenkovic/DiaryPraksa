package com.example.diarypraksa

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class NotificationUtils {

    private val NOTIFICATION_ID = 0
    private val REQUEST_CODE = 0
    private val FLAGS = 0


    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

        // TODO: Step 1.11 create intent
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        // TODO: Step 1.12 create PendingIntent
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }



}