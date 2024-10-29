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
import androidx.fragment.app.FragmentManager
import com.example.fokus.api.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var bottomNav: BottomNavigationView
    lateinit var musicPlayer: MediaPlayer
    private val settings = saveSettings()
    var switchedTabs = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        bottomNav = findViewById(R.id.bottomNavigation)

        musicPlayer = MediaPlayer.create(this, R.raw.fokus_one)
        musicPlayer.isLooping = true

        val volumeLevel = settings.getVolume(applicationContext)
        if (volumeLevel != null) {
            changeVolume(volumeLevel)
        }

        setupViewPagerAndTabs()

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bHome -> {
                    while (switchedTabs > 0) {
                        supportFragmentManager.popBackStack()
                        switchedTabs--
                    }
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

    fun changeMusic(track: Int) {
        musicPlayer.release()
        musicPlayer = MediaPlayer.create(this, track)
        val volumeLevel = settings.getVolume(applicationContext)
        if (volumeLevel != null) {
            changeVolume(volumeLevel)
        }
    }

    fun stopMusic() {
        if (musicPlayer.isPlaying) {
            musicPlayer.pause()
        }
    }

    fun changeVolume(volume: Int) {
        val clamped = volume.coerceIn(0, 100)
        val actualVolume = clamped / 100f
        musicPlayer.setVolume(actualVolume, actualVolume)
    }

    private fun setupViewPagerAndTabs() {
        val adapter = MainViewPagerAdapter(this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 6

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
        viewPager.visibility = View.VISIBLE
        tabLayout.visibility = View.VISIBLE
    }

    // load transaction
    private fun loadFragment(fragment: Fragment) {
        switchedTabs += 1
        // Check if fragment already exists in the back stack

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val existingFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.simpleName)

        fragmentTransaction.replace(R.id.main, fragment, fragment::class.java.simpleName)
        fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        fragmentTransaction.commit()

        // Hide ViewPager2 and TabLayout
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
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // show alertdialog
        builder.create().show()
    }
}
