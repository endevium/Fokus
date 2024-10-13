package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.R

class ForgotPasswordActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        backBtn.setOnClickListener{
            val Intent = Intent(this, LoginActivity::class.java)
            startActivity(Intent)

        }

}
}
