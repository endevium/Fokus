package com.example.fokus.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fokus.R
import com.example.fokus.SettingsFragment
import kotlin.concurrent.timer

class ThemesFragment : Fragment() {
    private lateinit var tvThemes: TextView
    private lateinit var defaultBtn: TextView
    private lateinit var natureBtn: TextView
    private lateinit var cafeBtn: TextView
    private lateinit var classicalBtn: TextView
    private lateinit var electronicBtn: TextView

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

        val mainActivity = requireActivity().findViewById<View>(R.id.main)
        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvThemes.setTextColor(color)
        })

        defaultBtn.setOnClickListener {
            mainActivity.setBackgroundColor(resources.getColor(R.color.white, null))
            viewModel.setTextColor(Color.BLACK)
        }

        natureBtn.setOnClickListener {
            mainActivity.setBackgroundResource(R.drawable.nature_bg)
            viewModel.setTextColor(Color.WHITE)
        }

        cafeBtn.setOnClickListener {
            mainActivity.setBackgroundResource(R.drawable.cafe_th)
            viewModel.setTextColor(Color.WHITE)
        }

        classicalBtn.setOnClickListener {
            mainActivity.setBackgroundResource(R.drawable.classical_th)
            viewModel.setTextColor(Color.WHITE)
        }

        electronicBtn.setOnClickListener {
            mainActivity.setBackgroundResource(R.drawable.electronic_th)
            viewModel.setTextColor(Color.WHITE)
        }
    }
}
