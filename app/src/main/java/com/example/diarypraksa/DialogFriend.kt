package com.example.diarypraksa

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.diarypraksa.DialogFeeling
import com.example.diarypraksa.R
import java.util.*

class DialogFriend(var c: Activity, var listener : INotifyFriend) : Dialog(c, R.style.Dialog), View.OnClickListener {

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_new_friend)

        val novoIme = findViewById<EditText>(R.id.editName)
        val prezime = findViewById<EditText>(R.id.editLastName)
        val email = findViewById<EditText>(R.id.editEmail)
        val telefon = findViewById<EditText>(R.id.editTelephone)
        val info = findViewById<EditText>(R.id.editInfo)


        val save = findViewById<ImageView>(R.id.buttonSaveFriend) as ImageView
        save.setOnClickListener{

            Toast.makeText(context, "prvo save" , Toast.LENGTH_SHORT).show()

            val today = Calendar.getInstance().time

            val friend =  Friend(novoIme.text.toString(), novoIme.text.toString(), prezime.text.toString(), today, email.text.toString(), info.text.toString(), telefon.text.toString())
            listener.onNewFriendAdded(friend)

            dismiss()
        }


    }

    fun editFriend(friend : Friend)
    {
        show()

        val firstAddedOnDate = friend.date

        var ime = findViewById<EditText>(R.id.editName)
            ime.setText(friend.name)
        var prezime = findViewById<EditText>(R.id.editLastName)
            prezime.setText(friend.lastName)
        var email = findViewById<EditText>(R.id.editEmail)
            email.setText(friend.email)
        var telefon = findViewById<EditText>(R.id.editTelephone)
            telefon.setText(friend.number)
        var info = findViewById<EditText>(R.id.editInfo)
            info.setText(friend.notes)


        val save = findViewById<ImageView>(R.id.buttonSaveFriend) as ImageView
        save.setOnClickListener{
            Toast.makeText(context, "drugo save" , Toast.LENGTH_SHORT).show()
            friend.name = ime.text.toString()
            friend.lastName = prezime.text.toString()
            friend.email = email.text.toString()
            friend.notes = info.text.toString()
            friend.number = telefon.text.toString()

            listener.onFriendUpdated(friend)
            dismiss()
        }


    }

    interface INotifyFriend{
        fun onNewNameAdded(novoIme : String)
        fun onNewFriendAdded(newFriend : Friend)
        fun onFriendUpdated(newFriend : Friend)

    }
}