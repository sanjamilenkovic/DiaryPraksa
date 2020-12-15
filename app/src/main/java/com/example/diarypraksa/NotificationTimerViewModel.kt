package com.example.diarypraksa

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.R
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationTimerViewModel(private val app: Application) : AndroidViewModel(app) {

    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private val notifyPendingIntent: PendingIntent

    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val notifyIntent = Intent(app, NotificationRecevier::class.java)

    private lateinit var timer: CountDownTimer

    init {


        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


    }


    private fun startTimer() {

        val selectedInterval = minute * 60 * 24


        val triggerTime = SystemClock.elapsedRealtime() + selectedInterval

        // TODO: Step 1.15 call cancel notification
        val notificationManager =
            ContextCompat.getSystemService(
                app,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelAll()

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            notifyPendingIntent
        )

    }

    fun resetTimer() {
        alarmManager.cancel(notifyPendingIntent)
        startTimer()
    }


}
