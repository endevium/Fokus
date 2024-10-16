package com.example.fokus.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fokus.R
import com.example.fokus.activities.MainActivity
import com.example.fokus.api.shortBreakSettings

class ShortBreakFragment : Fragment(R.layout.fragment_shortbreak) {

    private lateinit var timerTextView: TextView
    private lateinit var playButton: ImageButton
    private lateinit var restartButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var stopBtn: ImageButton
    private lateinit var timerFragment: TimerFragment
    private lateinit var tvPomodoro: TextView
    private lateinit var tvPomodoroDesc: TextView
    private val shrtbrk = shortBreakSettings()
    private var timer: CountDownTimer? = null
    private var timeLeft: Long = 5 * 60 * 1000
    private var isTimerRunning: Boolean = false
    private var phase: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shortbreak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assign values to element variables
        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        timerTextView = view.findViewById(R.id.timerTextView)
        playButton = view.findViewById(R.id.playButton)
        restartButton = view.findViewById(R.id.restartButton)
        nextButton = view.findViewById(R.id.sbnxtBtn)
        stopBtn = view.findViewById(R.id.stopBtn)
        tvPomodoro = view.findViewById(R.id.tvPomodoro)
        tvPomodoroDesc = view.findViewById(R.id.tvPomodoroDesc)
        timerFragment = TimerFragment()

        if (shrtbrk.shortbreakMinutes(requireContext()) != null && shrtbrk.shortbreakSeconds(requireContext()) != null) {
            val minutes = shrtbrk.shortbreakMinutes(requireContext()) ?: 5
            val seconds = shrtbrk.shortbreakSeconds(requireContext()) ?: 0
            timeLeft = (minutes * 60 * 1000) + (seconds * 1000)
            updateTimer()
        }

        val args = this.arguments
        val fetchedPhase = args?.getInt("phase")
        val fetchedAutoStart = args?.getBoolean("autoStart")

        // Start automatically if fetchedAutoStart exists and is true
        if (fetchedAutoStart != null && fetchedAutoStart) {
            startTimer()
        }

        // Fetch passed phase args if it exists
        if (fetchedPhase != null) {
            phase = fetchedPhase
        }

        // Start/pause timer if clicked
        playButton.setOnClickListener {
            (activity as? MainActivity)?.toggleMusic()
            if (!isTimerRunning) {
                startTimer()
            } else if (isTimerRunning) {
                isTimerRunning = false
                timer?.cancel()
            }
        }

        // Reset timer if clicked
        restartButton.setOnClickListener {
            (activity as? MainActivity)?.toggleMusic()
            resetTimer()
        }

        // Increment and pass pomodoro phase args and redirect back to TimerFragment
        nextButton.setOnClickListener {
            (activity as? MainActivity)?.toggleMusic()
            val bundle = Bundle()
            phase += 1
            bundle.putInt("phase", phase)

            /*
                Phases:
                0 -> Pomodoro
                1 -> Short Break
                2 -> Pomodoro
                3 -> Long Break
             */

            if (phase == 2) {
                timerFragment.arguments = bundle
                timerFragment()
            }
        }

        stopBtn.setOnClickListener {
            if (phase > 0) {
                phase = 0

                val bundle = Bundle()
                bundle.putInt("phase", phase)
                timerFragment.arguments = bundle
                timerFragment()
                Toast.makeText(requireContext(), "Pomodoro session ended", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvPomodoro.setTextColor(color)
            tvPomodoroDesc.setTextColor(color)
        })
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeft, 1000) {
            // Modify countdown time and update per tick
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimer()
            }

            // Increment phase and move to the next phase if timer hits 0
            override fun onFinish() {
                timerTextView.text = "00:00"
                isTimerRunning = false

                val bundle = Bundle()
                phase += 1
                bundle.putInt("phase", phase)
                bundle.putBoolean("autoStart", true)

                if (phase == 2) {
                    timerFragment.arguments = bundle
                    timerFragment()
                }
            }
        }.start()
        isTimerRunning = true
    }

    // Reset timer
    private fun resetTimer() {
        timer?.cancel()
        if (shrtbrk.shortbreakMinutes(requireContext()) != null && shrtbrk.shortbreakSeconds(requireContext()) != null) {
            val minutes = shrtbrk.shortbreakMinutes(requireContext()) ?: 5
            val seconds = shrtbrk.shortbreakSeconds(requireContext()) ?: 0
            timeLeft = (minutes * 60 * 1000) + (seconds * 1000)
            updateTimer()
        }

        isTimerRunning = false
    }

    // Update timerTextView
    private fun updateTimer() {
        val seconds = (timeLeft / 1000) % 60
        val minutes = (timeLeft / 1000) / 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    // Redirect back to TimerFragment
    private fun timerFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, timerFragment)
            .addToBackStack(null)
            .commit()
    }

    // Cancel countdown on destroy
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

}