package com.example.fokus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val signupD = findViewById<TextView>(R.id.signupD)

        signupD.setOnClickListener{
            val Intent = Intent(this, SignupActivity::class.java)
            startActivity(Intent)
        }
        val lBtn = findViewById<Button>(R.id.lBtn)

        lBtn.setOnClickListener{
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }

        }
    }
