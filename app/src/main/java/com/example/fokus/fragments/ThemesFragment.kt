package com.example.fokus.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fokus.R
import com.example.fokus.SettingsFragment
import com.example.fokus.activities.MainActivity
import com.example.fokus.api.saveSettings
import com.google.android.material.tabs.TabLayout
import kotlin.concurrent.timer

class ThemesFragment : Fragment() {
    private lateinit var tvThemes: TextView
    private lateinit var defaultBtn: TextView
    private lateinit var natureBtn: TextView
    private lateinit var cafeBtn: TextView
    private lateinit var classicalBtn: TextView
    private lateinit var electronicBtn: TextView
    private var musicPlaying = false
    private val save = saveSettings()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_themes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultBtn = view.findViewById(R.id.defaultBtn)
        natureBtn = view.findViewById(R.id.natureBtn)
        cafeBtn = view.findViewById(R.id.cafeBtn)
        classicalBtn = view.findViewById(R.id.classicalBtn)
        electronicBtn = view.findViewById(R.id.electronicBtn)
        tvThemes = view.findViewById(R.id.tvThemes)

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val savedTheme = save.getTheme(requireContext().applicationContext)

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvThemes.setTextColor(color)
        })

        when (savedTheme) {
            "default" -> {
                changeTheme("default", defaultBtn, R.color.white, Color.BLACK, R.color.DarkPurple, R.raw.fokus_one, R.drawable.add, R.drawable.arrowback)
                defaultBtn.isEnabled = false
                defaultBtn.text = "Selected"
            }
            "nature" -> changeTheme("nature", natureBtn, R.drawable.nature_bg, Color.WHITE, R.color.white, R.raw.fokus_nature, R.drawable.add_white, R.drawable.arrowback_white)
            "cafe" -> changeTheme("cafe", cafeBtn, R.drawable.cafe_th, Color.WHITE, R.color.white, R.raw.fokus_cafe, R.drawable.add_white, R.drawable.arrowback_white)
            "classical" -> changeTheme("classical", classicalBtn, R.drawable.classical_th, Color.WHITE, R.color.white, R.raw.fokus_classical, R.drawable.add_white, R.drawable.arrowback_white)
            "electronic" -> changeTheme("electronic", electronicBtn, R.drawable.electronic_th, Color.WHITE, R.color.white, R.raw.fokus_electronic, R.drawable.add_white, R.drawable.arrowback_white)
        }

        defaultBtn.setOnClickListener {
            changeTheme("default", defaultBtn, R.color.white, Color.BLACK, R.color.DarkPurple, R.raw.fokus_one, R.drawable.add, R.drawable.arrowback)
            Toast.makeText(requireContext().applicationContext, "Timer reset & theme changed successfully.", Toast.LENGTH_LONG).show()
        }

        natureBtn.setOnClickListener {
            changeTheme("nature", natureBtn, R.drawable.nature_bg, Color.WHITE, R.color.white, R.raw.fokus_nature, R.drawable.add_white, R.drawable.arrowback_white)
            Toast.makeText(requireContext().applicationContext, "Timer reset & theme changed successfully.", Toast.LENGTH_LONG).show()
        }

        cafeBtn.setOnClickListener {
            changeTheme("cafe", cafeBtn, R.drawable.cafe_th, Color.WHITE, R.color.white, R.raw.fokus_cafe, R.drawable.add_white, R.drawable.arrowback_white)
            Toast.makeText(requireContext().applicationContext, "Timer reset & theme changed successfully.", Toast.LENGTH_LONG).show()
        }

        classicalBtn.setOnClickListener {
            changeTheme("classical", classicalBtn, R.drawable.classical_th, Color.WHITE, R.color.white, R.raw.fokus_classical, R.drawable.add_white, R.drawable.arrowback_white)
            Toast.makeText(requireContext().applicationContext, "Timer reset & theme changed successfully.", Toast.LENGTH_LONG).show()
        }

        electronicBtn.setOnClickListener {
            changeTheme("electronic", electronicBtn, R.drawable.electronic_th, Color.WHITE, R.color.white, R.raw.fokus_electronic, R.drawable.add_white, R.drawable.arrowback_white)
            Toast.makeText(requireContext().applicationContext, "Timer reset & theme changed successfully.", Toast.LENGTH_LONG).show()
        }
    }

    private fun changeTheme(theme: String, button: TextView,
                            background: Int, color: Int, colorTwo: Int,
                            music: Int, addColor: Int, backColor: Int) {
        val buttons = listOf(defaultBtn, natureBtn, cafeBtn, classicalBtn, electronicBtn)

        for (btn in buttons) {
            if (btn.isEnabled.not() && btn.text == "Selected") {

                btn.isEnabled = true
                btn.text = "Select"
            }
        }

        button.isEnabled = false
        button.text = "Selected"

        val mainActivity = requireActivity().findViewById<View>(R.id.main)
        val tabLayout = mainActivity.findViewById<TabLayout>(R.id.tabLayout)
        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        musicPlaying = (requireActivity() as MainActivity).musicPlayer.isPlaying

        val tabTextColors = tabLayout.tabTextColors
        val defaultColor = tabTextColors?.defaultColor
        val unselectedColor = defaultColor ?: Color.GRAY

        mainActivity.setBackgroundResource(background)
        viewModel.setTextColor(color)
        viewModel.setAddColor(addColor)
        viewModel.setBackColor(backColor)
        viewModel.resetTimer()

        tabLayout.setTabTextColors(
            unselectedColor,
            ContextCompat.getColor(requireContext(), colorTwo)
        )

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), colorTwo))
        (requireActivity() as MainActivity).changeMusic(music)

        if (musicPlaying) {
            (requireActivity() as MainActivity).toggleMusic()
        }

        save.saveTheme(requireContext().applicationContext, theme)
    }
}
