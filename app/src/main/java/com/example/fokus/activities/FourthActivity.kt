package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.R

class FourthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fourth)

        val nxtBtn = findViewById<Button>(R.id.nxtBtn)

        nxtBtn.setOnClickListener{
            // Move to Signup activity
            val Intent = Intent(this, SignupActivity::class.java)
            startActivity(Intent)
        }

    }
}
