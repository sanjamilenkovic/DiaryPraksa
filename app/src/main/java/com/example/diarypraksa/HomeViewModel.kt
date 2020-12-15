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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.diarypraksa.MyApplication.Companion.currentApp
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: DiaryRepository,private val app: Application) : AndroidViewModel(app) {

    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private val notifyPendingIntent: PendingIntent

    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val notifyIntent = Intent(app, NotificationRecevier::class.java)

    private lateinit var timer: CountDownTimer

    init{
        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    }

     fun startTimer() {

        val selectedInterval = 5000


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

    fun insert(feeling: Feeling) = viewModelScope.launch {
        repository.insert(feeling)
    }


    class HomeViewModelFactory(private val repository: DiaryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository,currentApp) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}