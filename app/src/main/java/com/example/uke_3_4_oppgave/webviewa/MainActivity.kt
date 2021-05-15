package com.example.uke_3_4_oppgave.webviewa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uke_3_4_oppgave.R
import com.example.uke_3_4_oppgave.SHARED_PREFS_NAME
import com.example.uke_3_4_oppgave.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)


        /*supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment())
            .commitNow()*/
    }
        fun logOutUser() {
        getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }
}