package com.example.fokus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changeUsernameLayout: LinearLayout = view.findViewById(R.id.changeuserBtn)
        val changePasswordLayout: LinearLayout = view.findViewById(R.id.changepassBtn)
        val changeEmailLayout: LinearLayout = view.findViewById(R.id.changeemailBtn)
        val changePfpLayout: LinearLayout = view.findViewById(R.id.changepfpBtn)
        val changePTimerFragment: LinearLayout = view.findViewById(R.id.changeptimerBtn)
        val changeSBTimerFragment: LinearLayout = view.findViewById(R.id.changesbtimerBtn)
        val changeLBTimerFragment: LinearLayout = view.findViewById(R.id.changelbtimerBtn)

        changeUsernameLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangeUsernameFragment())
                .addToBackStack(null)
                .commit()
        }

        changePasswordLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangePasswordFragment())
                .addToBackStack(null)
                .commit()
        }
        changeEmailLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangeEmailFragment())
                .addToBackStack(null)
                .commit()
        }
        changePfpLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangePfpFragment())
                .addToBackStack(null)
                .commit()
        }
        changePTimerFragment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangePTimerFragment())
                .addToBackStack(null)
                .commit()
        }
        changeSBTimerFragment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangeSBTimerFragment())
                .addToBackStack(null)
                .commit()
        }
        changeLBTimerFragment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ChangeLBTimerFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
