package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.R

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)

        val iNxtBtn = findViewById<ImageButton>(R.id.inxtBtn)
        val iBckBtn = findViewById<ImageButton>(R.id.ibckBtn)

        // Move to fourth activity if clicked
        iNxtBtn.setOnClickListener {
            val Intent = Intent(this, FourthActivity::class.java)
            startActivity(Intent)

        }



        // Move to third activity if clicked
        iBckBtn.setOnClickListener{
            val Intent = Intent(this, SecondActivity::class.java)
            startActivity(Intent)
        }

    }
}
