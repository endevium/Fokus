package com.example.fokus.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import com.example.fokus.R

class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var timerTextView: TextView
    private lateinit var playButton: ImageButton
    private lateinit var restartButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var shortBreakFrag: ShortBreakFragment
    private lateinit var longBreakFrag: LongBreakFragment
    private var timer: CountDownTimer? = null
    private var timeLeft: Long = 1 * 60 * 1000
    private var isTimerRunning: Boolean = false
    private var phase: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assign values to element variables
        timerTextView = view.findViewById(R.id.timerTextView)
        playButton = view.findViewById(R.id.playButton)
        restartButton = view.findViewById(R.id.restartButton)
        nextButton = view.findViewById(R.id.tnxtBtn)
        shortBreakFrag = ShortBreakFragment()
        longBreakFrag = LongBreakFragment()
        val args = this.arguments
        val fetchedPhase = args?.getInt("phase")
        val fetchedAutoStart = args?.getBoolean("autoStart")

        // Start automatically if fetchedAutoStart exists and is true
        if (fetchedAutoStart != null && fetchedAutoStart) {
            startTimer()
        }

        // Fetch passed phase argument if it exists
        if (fetchedPhase != null) {
            phase = fetchedPhase
        }

        // Start/pause timer if clicked
        playButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            } else if (isTimerRunning) {
                isTimerRunning = false
                timer?.cancel()
            }
        }

        // Reset timer if clicked
        restartButton.setOnClickListener {
            resetTimer()
        }

        // Increment and pass pomodoro phase args and move to the next phase
        nextButton.setOnClickListener {
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

            if (phase == 1) {
                shortBreakFrag.arguments = bundle
                shortBreakFragment()
            } else if (phase == 3) {
                longBreakFrag.arguments = bundle
                longBreakFragment()
            }
        }
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

                /*
                    Phases:
                    0 -> Pomodoro
                    1 -> Short Break
                    2 -> Pomodoro
                    3 -> Long Break
                */

                if (phase == 1) {
                    shortBreakFrag.arguments = bundle
                    shortBreakFragment()
                } else if (phase == 3) {
                    longBreakFrag.arguments = bundle
                    longBreakFragment()
                }
            }
        }.start()
        isTimerRunning = true
    }

    // Reset timer
    private fun resetTimer() {
        timer?.cancel()
        timeLeft = 25 * 60 * 1000 // Default time is 25 minutes

        updateTimer()
        isTimerRunning = false
    }

    // Update timerTextView
    private fun updateTimer() {
        val seconds = (timeLeft / 1000) % 60
        val minutes = (timeLeft / 1000) / 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    // Redirect to ShortBreakFragment
    private fun shortBreakFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, shortBreakFrag)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    // Redirect to LongBreakFragment
    private fun longBreakFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, longBreakFrag)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    // Cancel countdown on destroy
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}