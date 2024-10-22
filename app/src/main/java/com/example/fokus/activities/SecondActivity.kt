package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.R


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val signupBtn = findViewById<TextView>(R.id.signupBtn)
        val loginBtn = findViewById<TextView>(R.id.loginBtn)

        // Move to Sign Up activity if signupBtn is clicked
        signupBtn.setOnClickListener {
            signupBtn.isEnabled = false
            loginBtn.isEnabled = false
            val Intent = Intent(this, SignupActivity::class.java)
            startActivity(Intent)
            finish()
        }

        // Move to Log in if signUp is clicked
        loginBtn.setOnClickListener {
            signupBtn.isEnabled = false
            loginBtn.isEnabled = false
            val Intent = Intent(this, LoginActivity::class.java)
            startActivity(Intent)
            finish()
        }
    }
}