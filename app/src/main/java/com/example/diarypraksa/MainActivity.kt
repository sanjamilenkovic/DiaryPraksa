package com.example.diarypraksa

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController
        setupBottomNavMenu(navController)


//        val fab: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener {
//            Toast.makeText(this, "text text", Toast.LENGTH_SHORT).show()
//        }
//

//        val callBestFriendButton: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab1)
//        callBestFriendButton.setOnClickListener {
//            Toast.makeText(this, "Call best friend", Toast.LENGTH_SHORT).show()
//        }

//        val chatBotButton: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab2)
//        chatBotButton.setOnClickListener {
//            Toast.makeText(this, "Talk with me", Toast.LENGTH_SHORT).show()
//        }


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