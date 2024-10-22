package com.example.fokus

import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fokus.activities.MainActivity
import com.example.fokus.api.saveSettings
import com.example.fokus.fragments.SharedViewModel

class SettingsFragment : Fragment() {
    private lateinit var tvSettings: TextView
    private lateinit var tvAccountSettings: TextView
    private lateinit var tvPomodoroSettings: TextView
    private lateinit var tvSoundSettings: TextView
    private lateinit var silentBtn: Switch
    private lateinit var increase: ImageButton
    private lateinit var decrease: ImageButton
    private lateinit var volume: TextView
    private val save = saveSettings()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val changeUsernameLayout: LinearLayout = view.findViewById(R.id.changeuserBtn)
        val changePasswordLayout: LinearLayout = view.findViewById(R.id.changepassBtn)
        val changeEmailLayout: LinearLayout = view.findViewById(R.id.changeemailBtn)
        val changePfpLayout: LinearLayout = view.findViewById(R.id.changepfpBtn)
        val changePTimerFragment: LinearLayout = view.findViewById(R.id.changeptimerBtn)
        val changeSBTimerFragment: LinearLayout = view.findViewById(R.id.changesbtimerBtn)
        val changeLBTimerFragment: LinearLayout = view.findViewById(R.id.changelbtimerBtn)
        val handler = Handler(Looper.getMainLooper())
        var isIncreasing = false
        var isDecreasing = false

        tvSettings = view.findViewById(R.id.tvSettings)
        tvAccountSettings = view.findViewById(R.id.tvAccountSettings)
        tvPomodoroSettings = view.findViewById(R.id.tvPomodoroSettings)
        tvSoundSettings = view.findViewById(R.id.tvSoundSettings)
        silentBtn = view.findViewById(R.id.silentModeBtn)
        increase = view.findViewById(R.id.increaseBtn)
        decrease = view.findViewById(R.id.reduceBtn)
        volume = view.findViewById(R.id.volumeTt)

        silentBtn.isChecked = save.getSilent(requireContext().applicationContext) ?: false
        volume.text = save.getVolume(requireContext().applicationContext).toString()

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

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvSettings.setTextColor(color)
            tvAccountSettings.setTextColor(color)
            tvPomodoroSettings.setTextColor(color)
            tvSoundSettings.setTextColor(color)
        })

        silentBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                volume.text = "0"

                (requireActivity() as MainActivity).changeVolume(0)
                save.saveSilent(requireContext().applicationContext, true)
                save.saveVibration(requireContext().applicationContext, false)
                save.saveVolume(requireContext().applicationContext, 0)
            } else {
                save.saveSilent(requireContext().applicationContext, false)
            }
        }

        val increaseVolume = object : Runnable {
            override fun run() {
                val currentVolume = volume.text.toString().toInt()
                if (silentBtn.isChecked && isIncreasing) {
                    silentBtn.isChecked = false
                }

                if (currentVolume < 100 && isIncreasing) {
                    val newVolume = currentVolume + 1
                    volume.text = newVolume.toString()

                    (requireActivity() as MainActivity).changeVolume(newVolume)
                    save.saveSilent(requireContext().applicationContext, false)
                    save.saveVolume(requireContext().applicationContext, newVolume)

                    handler.postDelayed(this, 100)
                }
            }
        }

        val decreaseVolume = object : Runnable {
            override fun run() {
                val currentVolume = volume.text.toString().toInt()
                if (currentVolume > 0 && isDecreasing) {
                    val newVolume = currentVolume - 1
                    volume.text = newVolume.toString()

                    (requireActivity() as MainActivity).changeVolume(newVolume)
                    save.saveVolume(requireContext().applicationContext, newVolume)

                    handler.postDelayed(this, 100)
                }
            }
        }

        increase.setOnClickListener {
            val currentVolume = volume.text.toString().toInt()
            if (currentVolume < 100) {
                if (silentBtn.isChecked) {
                    silentBtn.isChecked = false
                }
                val newVolume = currentVolume + 1
                volume.text = newVolume.toString()

                (requireActivity() as MainActivity).changeVolume(newVolume)
                save.saveSilent(requireContext().applicationContext, false)
                save.saveVolume(requireContext().applicationContext, newVolume)
            }
        }

        increase.setOnLongClickListener {
            isIncreasing = true
            handler.post(increaseVolume)
            true
        }

        increase.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isIncreasing = false
            }
            false
        }

        decrease.setOnClickListener {
            val currentVolume = volume.text.toString().toInt()
            if (currentVolume > 0) {
                val newVolume = currentVolume - 1
                volume.text = newVolume.toString()

                (requireActivity() as MainActivity).changeVolume(newVolume)
                save.saveVolume(requireContext().applicationContext, newVolume)
            }
        }

        decrease.setOnLongClickListener {
            isDecreasing = true
            handler.post(decreaseVolume)
            true
        }

        decrease.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isDecreasing = false
            }
            false
        }
    }
}
