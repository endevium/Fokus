package com.example.fokus.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.fokus.R


class GuideFragment : Fragment(R.layout.fragment_guide) {


    private lateinit var exitButton: ImageButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        exitButton = view.findViewById(R.id.exitBtn)


        exitButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }
}

