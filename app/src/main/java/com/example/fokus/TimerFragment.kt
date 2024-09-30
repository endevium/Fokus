package com.example.fokus

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ImageButton

class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var timerTextView: TextView
    private lateinit var playButton: ImageButton
    private lateinit var restartButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var shortBreakFragment: ShortBreakFragment
    private lateinit var longBreakFragment: LongBreakFragment
    private var timer: CountDownTimer? = null
    private var timeLeft: Long = 25 * 60 * 1000
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

        timerTextView = view.findViewById(R.id.timerTextView)
        playButton = view.findViewById(R.id.playButton)
        restartButton = view.findViewById(R.id.restartButton)
        nextButton = view.findViewById(R.id.tnxtBtn)
        shortBreakFragment = ShortBreakFragment()
        longBreakFragment = LongBreakFragment()

        val args = this.arguments
        val fetchedPhase = args?.getInt("phase")
        val fetchedAutoStart = args?.getBoolean("autoStart")

        if (fetchedAutoStart != null && fetchedAutoStart) {
            startTimer()
        }

        if (fetchedPhase != null) {
            phase = fetchedPhase
        }

        playButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            } else if (isTimerRunning) {
                isTimerRunning = false
                timer?.cancel()
            }
        }

        restartButton.setOnClickListener {
            resetTimer()
        }

        nextButton.setOnClickListener {
            val bundle = Bundle()
            phase += 1
            bundle.putInt("phase", phase)

            if (phase == 1) {
                shortBreakFragment.arguments = bundle
                shortBreakFragment()
            } else if (phase == 3) {
                longBreakFragment.arguments = bundle
                longBreakFragment()
            }
        }
    }


    private fun startTimer() {
        timer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                isTimerRunning = false

                val bundle = Bundle()
                phase += 1
                bundle.putInt("phase", phase)
                bundle.putBoolean("autoStart", true)

                if (phase == 1) {
                    shortBreakFragment.arguments = bundle
                    shortBreakFragment()
                } else if (phase == 3) {
                    longBreakFragment.arguments = bundle
                    longBreakFragment()
                }
            }
        }.start()
        isTimerRunning = true
    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeft = 25 * 60 * 1000
        updateTimer()
        isTimerRunning = false
    }

    private fun updateTimer() {
        val seconds = (timeLeft / 1000) % 60
        val minutes = (timeLeft / 1000) / 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun shortBreakFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, shortBreakFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun longBreakFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, longBreakFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

}