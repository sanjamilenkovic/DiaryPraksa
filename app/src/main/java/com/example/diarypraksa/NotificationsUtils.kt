package com.example.diarypraksa

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import kotlin.coroutines.coroutineContext



    private val NOTIFICATION_ID = 0
    private val REQUEST_CODE = 0
    private val FLAGS = 0


    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


        val contentIntent = Intent(applicationContext, MainActivity::class.java)

        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder=NotificationCompat.Builder(applicationContext,applicationContext.getString(R.string.messageBody))
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.sticker_1)
            .setContentTitle("DepressionDiary")
            .setContentText(messageBody)
        notify(NOTIFICATION_ID,builder.build())

        val stickerImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.sticker_1
        )
        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(stickerImage)
            .bigLargeIcon(null)

    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }

}