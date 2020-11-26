package com.example.diarypraksa

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class DialogFeeling(var c: Activity, val listener : INotifyFeeling) : Dialog(c, R.style.Dialog), View.OnClickListener
    {
        lateinit var d: Dialog
        lateinit var cancel: Button
        lateinit var save: Button
        lateinit var edit : EditText


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_feeling)

            cancel = findViewById<View>(R.id.btn_cancel) as Button
            save = findViewById<View>(R.id.btn_save) as Button
            edit = findViewById<EditText>(R.id.i_feel_et_large) as EditText


            cancel.setOnClickListener{
                dismiss()
            }

            save.setOnClickListener{
                val opis = edit.text.toString()
                listener.onMoodDescriptionAdded(opis)
                dismiss()
            }

        }

        override fun onClick(v: View) {
//            when (v.id) {
//                R.id.btn_cancel -> dismiss()
//                R.id.btn_save -> {

            }

        interface INotifyFeeling{
            fun onMoodDescriptionAdded(opis : String)
        }
    }
