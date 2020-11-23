package com.example.diarypraksa

import android.app.Application

class MyApplication: Application() {
    init {
        currentApp = this
    }
    companion object{
        lateinit var currentApp: MyApplication
    }
}