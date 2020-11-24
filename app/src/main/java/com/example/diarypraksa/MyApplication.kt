package com.example.diarypraksa

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {
    init {
        currentApp = this
    }
    companion object{
        lateinit var currentApp: MyApplication
    }

    val applicationScope  = CoroutineScope(SupervisorJob())

    val database by lazy {DiaryRoomDatabase.getDatabase(this, applicationScope)}

    val repository by lazy { DiaryRepository(database.feelingDao(), database.friendDao())}


}