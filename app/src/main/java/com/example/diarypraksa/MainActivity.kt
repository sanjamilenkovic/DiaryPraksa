package com.example.diarypraksa

import android.content.Context
import android.os.Bundle
import android.text.style.QuoteSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        setupBottomNavMenu(navController)

        deserializeQuestion()

        val callBestFriendButton : FloatingActionButton =findViewById<FloatingActionButton>(R.id.buttonTalkToFriend)
        callBestFriendButton.setOnClickListener {
            Toast.makeText(this, "Calling friend", Toast.LENGTH_SHORT).show()
        }

        val openChatBotButton : FloatingActionButton =findViewById<FloatingActionButton>(R.id.buttonChatBot)
        openChatBotButton.setOnClickListener {
            Toast.makeText(this, "Opening chat bot", Toast.LENGTH_SHORT).show()
        }
    }

    fun deserializeQuestion() {

        var listaPitanja = ArrayList<Question>()
        val gson = Gson()

        val listType = object : TypeToken<ArrayList<Question>>() {}.type
        listaPitanja = gson.fromJson<ArrayList<Question>>(loadJSONFromAsset(this, "Test2.json") , listType)
        //Toast.makeText(this, listaPitanja[1].questionText, Toast.LENGTH_SHORT).show()
    }

    fun loadJSONFromAsset(context: Context, path: String, language: String? = null): String? {
        val json: String
        try {
            val `is` = context.assets.open(path)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)

        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.onRequestPermissionsResult(
            requestCode,
            permissions as Array<String>,
            grantResults
        )
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.selectedItemId = R.id.home_dest
        bottomNav?.setupWithNavController(navController)

    }


}