package com.example.fokus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)

        val adapter = MainViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Timer"
                1 -> tab.text = "Task"
                2 -> tab.text = "Theme"
                3 -> tab.text = "Notes"
            }
        }.attach()

        bottomNavigationView.selectedItemId = R.id.bHome

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bHome -> {
                    true
                }
                R.id.bSettings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)  // No animation
                    true
                }
                R.id.bProfile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)  // No animation
                    true
                }
                else -> false
            }
        }
    }
}
