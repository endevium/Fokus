package com.example.fokus

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ImageButton

class   LongBreakFragment : Fragment(R.layout.fragment_longbreak) {

    private lateinit var timerTextView: TextView
    private lateinit var playButton: ImageButton
    private lateinit var restartButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var timerFragment: TimerFragment
    private var timer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 15 * 60 * 1000
    private var isTimerRunning: Boolean = false
    private var phase: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_longbreak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerTextView = view.findViewById(R.id.timerTextView)
        playButton = view.findViewById(R.id.playButton)
        restartButton = view.findViewById(R.id.restartButton)
        nextButton = view.findViewById(R.id.sbnxtBtn)
        timerFragment = TimerFragment()

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
            phase = 0
            bundle.putInt("phase", phase)

            timerFragment.arguments = bundle
            timerFragment()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                isTimerRunning = false

                val bundle = Bundle()
                phase = 0
                bundle.putInt("phase", phase)
                bundle.putBoolean("autoStart", true)

                timerFragment.arguments = bundle
                timerFragment()
            }
        }.start()
        isTimerRunning = true
    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeftInMillis = 15 * 60 * 1000
        updateTimer()
        isTimerRunning = false
    }

    private fun updateTimer() {
        val seconds = (timeLeftInMillis / 1000) % 60
        val minutes = (timeLeftInMillis / 1000) / 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun timerFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, timerFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

}