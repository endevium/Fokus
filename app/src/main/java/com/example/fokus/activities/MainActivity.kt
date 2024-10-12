package com.example.fokus.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fokus.fragments.MainViewPagerAdapter
import com.example.fokus.fragments.ProfileFragment
import com.example.fokus.R
import com.example.fokus.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.activity.addCallback
import com.example.fokus.api.clearToken

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var musicPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        bottomNav = findViewById(R.id.bottomNavigation)

        musicPlayer = MediaPlayer.create(this, R.raw.fokus_one)
        musicPlayer.isLooping = true

        setupViewPagerAndTabs()

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bHome -> {
                    showMainScreen()
                    true
                }
                R.id.bSettings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                R.id.bProfile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            closeAlert()
        }
    }

    fun toggleMusic() {
        if (!musicPlayer.isPlaying) {
            musicPlayer.start()
        } else {
            musicPlayer.pause()
        }
    }

    private fun setupViewPagerAndTabs() {
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

        viewPager.visibility = View.VISIBLE
        tabLayout.visibility = View.VISIBLE
    }

    // show main (reset viewPager2 and tabLayout)
    private fun showMainScreen() {
        // remove fragments to main
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)

        // show viewPager2 and tabLayout
        viewPager.visibility = View.VISIBLE
        tabLayout.visibility = View.VISIBLE
    }

    // load transaction
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

        // hide viewPager2 and tabLayout when in fragment xD
        viewPager.visibility = View.GONE
        tabLayout.visibility = View.GONE
    }

    // closing alertdialog builder
    private fun closeAlert() {
        // build alertdialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Fokus")
        builder.setMessage("Are you sure you want to close the application?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            clearToken(applicationContext)
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // show alertdialog
        builder.create().show()
    }
}
