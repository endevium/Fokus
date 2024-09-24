package com.example.fokus

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.ImageButton

class ShortBreakFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var playButton: ImageButton
    private lateinit var restartButton: ImageButton
    private var timer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 5 * 60 * 1000
    private var isTimerRunning: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shortbreak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        timerTextView = view.findViewById(R.id.timerTextView)
        playButton = view.findViewById(R.id.playButton)
        restartButton = view.findViewById(R.id.restartButton)

        playButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            }
        }

        restartButton.setOnClickListener {
            resetTimer()
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
            }
        }.start()
        isTimerRunning = true
    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeftInMillis = 5 * 60 * 1000
        updateTimer()
        isTimerRunning = false
    }

    private fun updateTimer() {
        val seconds = (timeLeftInMillis / 1000) % 60
        val minutes = (timeLeftInMillis / 1000) / 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

}