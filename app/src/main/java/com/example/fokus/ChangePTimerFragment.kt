package com.example.fokus

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class ChangePTimerFragment : Fragment(R.layout.fragment_changeptimer) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: ImageButton = view.findViewById(R.id.backBtn)

        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}