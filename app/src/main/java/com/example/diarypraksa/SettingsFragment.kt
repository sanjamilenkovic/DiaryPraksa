package com.example.diarypraksa

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.friends_rv_item.*

class SettingsFragment : Fragment(), IPermissionListener {
    private var IMAGE_PICK_CODE: Int = 10
    lateinit var imageView: ImageView
    lateinit var buttonP: ImageButton
    lateinit var inputName: EditText
    lateinit var inputSurname: EditText
    lateinit var inputPhone: EditText
    lateinit var string: String
    // val SharedPreferences = mPreferences
    // lateinit var btnSave=Button


    //  private var mPreferences: SharedPreferences?=null
    private val sharedPrefFile = "com.example.diarypraksa"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        }




    // val mPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)



    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            IMAGE_PICK_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageView = requireView().findViewById<ImageView>(R.id.imageView6)
            imageView.setImageURI(data?.data)
            string=data?.data.toString()
            Glide.with(requireContext()).load(string.toUri()).into(imageView)

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.imageView6)
        inputName = view.findViewById(R.id.editName)
        inputSurname = view.findViewById(R.id.editSurname)
        inputPhone = view.findViewById(R.id.editPhone)
        val btnSave = requireView().findViewById<Button>(R.id.butSave)
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        btnSave.setOnClickListener {
            Toast.makeText(requireContext(), "Your changes have been saved", Toast.LENGTH_SHORT).show()


            val name: String = inputName.text.toString()
            val surname: String = inputSurname.text.toString()
            val phone: String = inputPhone.text.toString()
            val photos: String = string
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.putString("picture_key", string)
            editor.putString("name_key", inputName.text.toString())
            editor.putString("surname_key", inputSurname.text.toString())
            editor.putString("phone_key", inputPhone.text.toString())

            editor.apply()

        }





        Glide.with(requireContext()).load(sharedPreferences.getString("picture_key","")!!.toUri()).into(imageView)
        inputName.setText(sharedPreferences.getString("name_key",""))
        inputSurname.setText(sharedPreferences.getString("surname_key",""))
        inputPhone.setText(sharedPreferences.getString("phone_key",""))


        buttonP = view.findViewById(R.id.requestP)
        buttonP.setOnClickListener {


            PermissionHelper.requestStoragePermission(requireActivity(), this)


        }

    }

    override fun permissionAllowed() {
        pickImageFromGallery()
    }

    override fun permissionDenied() {

    }


}

