package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.R
import com.example.fokus.api.RetrofitClient
import com.example.fokus.api.getToken

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // Move to second activity after delay time
            if (getToken(applicationContext) == null) {
                startActivity(Intent(this, SignupActivity::class.java))
                finish()
            } else {
                getToken(applicationContext)?.let { RetrofitClient.setToken(it) }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000) // 2 seconds delay
    }
}