package com.example.diarypraksa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        setupBottomNavMenu(navController)




        val callBestFriendButton: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab1)
        callBestFriendButton.setOnClickListener {
            Toast.makeText(this, "Call best friend", Toast.LENGTH_SHORT).show()
        }

        val chatBotButton: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab2)
        chatBotButton.setOnClickListener {

            val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment

            // Set up Action Bar
            val navController = host.navController



        }


    }

//    btn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(MainActivity.this, MyOtherActivity.class));
//        }

    fun jsonToObject(){
        var listOfAnswers = ArrayList<Question>()
        var gson = Gson()



        val listType = object : TypeToken<ArrayList<Question>>() {}.type
        listOfAnswers =
            Gson().fromJson<ArrayList<Question>>(
                loadJSONFromAsset(
                    this,
                   "proba.json"
                ), listType
            )


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


    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
//        bottomNav?.setOnNavigationItemSelectedListener {
//            when (it.itemId)
//            {
//                R.id.home_dest -> {}
//            }
//            true }
        bottomNav?.selectedItemId = R.id.home_dest
        bottomNav?.setupWithNavController(navController)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.onRequestPermissionsResult(
            requestCode,
            permissions as Array<String>, grantResults
        )
    }
}