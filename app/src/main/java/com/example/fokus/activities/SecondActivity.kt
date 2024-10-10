package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.R


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val iNxtBtn = findViewById<ImageButton>(R.id.inxtBtn)

        // Move to third activity if next button is clicked
        iNxtBtn.setOnClickListener {
            val Intent = Intent(this, ThirdActivity::class.java)
            startActivity(Intent)
        }
    }
}