package com.example.finaltaskmanager.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltaskmanager.R

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        sharedPreferences =getSharedPreferences("userData", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val username: String = sharedPreferences.getString("username", "").toString()

            if(username.isEmpty()){
                val intent=Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)

            }
            val intent=Intent(this@SplashActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        },1000)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}