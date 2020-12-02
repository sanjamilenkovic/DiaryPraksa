package com.example.diarypraksa

import android.app.Activity
import android.app.Dialog
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import java.util.*
import android.Manifest
import android.annotation.SuppressLint

class DialogFriend(var c: Activity, var listener: INotifyFriend, var homeFragment: HomeFragment) :
    Dialog(c, R.style.Dialog), View.OnClickListener {

    lateinit var novoIme: EditText
    lateinit var prezime: EditText
    lateinit var email: EditText
    lateinit var telefon: EditText
    lateinit var image: ImageView
    lateinit var info: EditText
    lateinit var imagePath : String

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    private var permissionListener = object : IPermissionListener {
        override fun permissionAllowed() {
            val getImageIntent = Intent(Intent.ACTION_GET_CONTENT)
            getImageIntent.addCategory(Intent.CATEGORY_OPENABLE)
            getImageIntent.type = "image/*"

            val INPUT_FILE_REQUEST_CODE = 1;
            homeFragment.startActivityForResult(getImageIntent, INPUT_FILE_REQUEST_CODE)
        }

        override fun permissionDenied() {
            Toast.makeText(context, "Denied", Toast.LENGTH_LONG )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_new_friend)

        novoIme = findViewById<EditText>(R.id.editName)
        prezime = findViewById<EditText>(R.id.editLastName)
        email = findViewById<EditText>(R.id.editEmail)
        telefon = findViewById<EditText>(R.id.editTelephone)
        info = findViewById<EditText>(R.id.editInfo)
        image = findViewById<ImageView>(R.id.imageNewFriend)
        imagePath = novoIme.text.toString()

        image.setOnClickListener {
            PermissionHelper.requestStoragePermission(c, permissionListener)
        }

        val save = findViewById<ImageView>(R.id.buttonSaveFriend) as ImageView
        save.setOnClickListener {

            val today = Calendar.getInstance().time
            val friend = Friend(
                imagePath,
                novoIme.text.toString(),
                prezime.text.toString(),
                today,
                email.text.toString(),
                info.text.toString(),
                telefon.text.toString()
            )
            listener.onNewFriendAdded(friend)
            dismiss()
        }
    }

    fun getDataString(dataString: String) {
        imagePath = dataString
        val uri = imagePath.toUri()
        image.setImageURI(uri)
    }


    fun editFriend(friend: Friend) {
        show()

        novoIme.setText(friend.name)
        prezime.setText(friend.lastName)
        email.setText(friend.email)
        telefon.setText(friend.number)
        info.setText(friend.notes)

//      Glide koristiti uvek pri ucitavanju slike
        Glide.with(context).load(friend.image.toUri()).dontAnimate().into(image)

        image.setOnClickListener {
            PermissionHelper.requestStoragePermission(c, permissionListener)
        }

        //image.setImageURI(friend.image.toUri())

//        imageViewFriend.setOnClickListener {
//
//            val getImageIntent = Intent(Intent.ACTION_GET_CONTENT)
//            getImageIntent.addCategory(Intent.CATEGORY_OPENABLE)
//            getImageIntent.type = "image/*"
//
//            val INPUT_FILE_REQUEST_CODE = 1;
//
//            homeFragment.startActivityForResult(getImageIntent, INPUT_FILE_REQUEST_CODE)
//        }


        val save = findViewById<ImageView>(R.id.buttonSaveFriend) as ImageView
        save.setOnClickListener {
            friend.name = novoIme.text.toString()
            friend.lastName = prezime.text.toString()
            friend.email = email.text.toString()
            friend.notes = info.text.toString()
            friend.number = telefon.text.toString()
            friend.image = imagePath
            listener.onFriendUpdated(friend)
            dismiss()
        }
    }

    interface INotifyFriend {
        fun onNewNameAdded(novoIme: String)
        fun onNewFriendAdded(newFriend: Friend)
        fun onFriendUpdated(newFriend: Friend)
    }

}