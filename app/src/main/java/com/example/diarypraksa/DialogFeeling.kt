package com.example.diarypraksa

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button



class DialogFeeling(var c: Activity) : Dialog(c), View.OnClickListener
    {
        var d: Dialog? = null
        var cancel: Button? = null
        var no: Button? = null


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_feeling)

            cancel = findViewById<View>(R.id.btn_cancel) as Button
            no = findViewById<View>(R.id.btn_ok) as Button


            cancel!!.setOnClickListener(this)
            no!!.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (v.getId()) {
                R.id.btn_ok -> c.finish()
                R.id.btn_cancel -> dismiss()
                else -> {
                }
            }
            dismiss()
        }

    }