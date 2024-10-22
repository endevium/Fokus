package com.example.fokus.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.google.android.material.tabs.TabLayout
import kotlin.concurrent.timer

class ThemesFragment : Fragment() {
    private lateinit var tvThemes: TextView
    private lateinit var defaultBtn: TextView
    private lateinit var natureBtn: TextView
    private lateinit var cafeBtn: TextView
    private lateinit var classicalBtn: TextView
    private lateinit var electronicBtn: TextView
    private lateinit var timerFragment: TimerFragment
    private lateinit var shortBreakFragment: ShortBreakFragment
    private lateinit var longBreakFragment: LongBreakFragment

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

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvThemes.setTextColor(color)
        })

        defaultBtn.isEnabled = false
        defaultBtn.text = "Selected"
        defaultBtn.setOnClickListener {
            changeTheme(defaultBtn, R.color.white, Color.BLACK, R.color.DarkPurple, R.raw.fokus_one)
        }

        natureBtn.setOnClickListener {
            changeTheme(natureBtn, R.drawable.nature_bg, Color.WHITE, R.color.white, R.raw.fokus_nature)
        }

        cafeBtn.setOnClickListener {
            changeTheme(cafeBtn, R.drawable.cafe_th, Color.WHITE, R.color.white, R.raw.fokus_cafe)
        }

        classicalBtn.setOnClickListener {
            changeTheme(classicalBtn, R.drawable.classical_th, Color.WHITE, R.color.white, R.raw.fokus_classical)
        }

        electronicBtn.setOnClickListener {
            changeTheme(electronicBtn, R.drawable.electronic_th, Color.WHITE, R.color.white, R.raw.fokus_electronic)
        }
    }

    private fun changeTheme(button: TextView, background: Int, color: Int, colorTwo: Int, music: Int) {
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

        val tabTextColors = tabLayout.tabTextColors
        val defaultColor = tabTextColors?.defaultColor
        val unselectedColor = defaultColor ?: Color.GRAY

        mainActivity.setBackgroundResource(background)
        viewModel.setTextColor(color)

        tabLayout.setTabTextColors(
            unselectedColor,
            ContextCompat.getColor(requireContext(), colorTwo)
        )

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), colorTwo))
        (requireActivity() as MainActivity).changeMusic(music)
    }
}
