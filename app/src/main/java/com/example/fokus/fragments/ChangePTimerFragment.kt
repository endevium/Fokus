package com.example.fokus


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fokus.api.loadPomodoroSettings
import com.example.fokus.api.pomodoroSettings


class ChangePTimerFragment : Fragment(R.layout.fragment_changeptimer) {
    private lateinit var firstUpBtn: ImageButton
    private lateinit var firstDigit: TextView
    private lateinit var firstDownBtn: ImageButton
    private lateinit var secondUpBtn: ImageButton
    private lateinit var secondDigit: TextView
    private lateinit var secondDownBtn: ImageButton
    private lateinit var thirdUpBtn: ImageButton
    private lateinit var thirdDigit: TextView
    private lateinit var thirdDownBtn: ImageButton
    private lateinit var fourthUpBtn: ImageButton
    private lateinit var fourthDigit: TextView
    private lateinit var fourthDownBtn: ImageButton
    private lateinit var backBtn: ImageButton
    private lateinit var resetBtn: TextView
    private lateinit var saveBtn: TextView
    private lateinit var currentDigitTextView: TextView
    private val pmdr = pomodoroSettings()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstUpBtn = view.findViewById(R.id.firstUpBtn)
        secondUpBtn = view.findViewById(R.id.secondUpBtn)
        thirdUpBtn = view.findViewById(R.id.thirdUpBtn)
        fourthUpBtn = view.findViewById(R.id.fourthUpBtn)

        firstDigit = view.findViewById(R.id.firstDigit)
        secondDigit = view.findViewById(R.id.secondDigit)
        thirdDigit = view.findViewById(R.id.thirdDigit)
        fourthDigit = view.findViewById(R.id.fourthDigit)

        firstDownBtn = view.findViewById(R.id.firstDownBtn)
        secondDownBtn = view.findViewById(R.id.secondDownBtn)
        thirdDownBtn = view.findViewById(R.id.thirdDownBtn)
        fourthDownBtn = view.findViewById(R.id.fourthDownBtn)

        resetBtn = view.findViewById(R.id.resetBtn)
        backBtn = view.findViewById(R.id.backBtn)
        saveBtn = view.findViewById(R.id.saveBtn)

        val settings = loadPomodoroSettings()

        if (settings.loadFirstDigit(requireContext()) != null &&
            settings.loadSecondDigit(requireContext()) != null &&
            settings.loadThirdDigit(requireContext()) != null &&
            settings.loadFourthDigit(requireContext()) != null) {

            firstDigit.text = settings.loadFirstDigit(requireContext())
            secondDigit.text = settings.loadSecondDigit(requireContext())
            thirdDigit.text = settings.loadThirdDigit(requireContext())
            fourthDigit.text = settings.loadFourthDigit(requireContext())
        }

        val handler = Handler(Looper.getMainLooper())
        var isIncreasing = false
        var isDecreasing = false

        val increaseDigitRunnable = object : Runnable {
            override fun run() {
                if (isIncreasing) {
                    addDigit(currentDigitTextView)
                    handler.postDelayed(this, 100)
                }
            }
        }

        val decreaseDigitRunnable = object : Runnable {
            override fun run() {
                if (isDecreasing) {
                    subtractDigit(currentDigitTextView)
                    handler.postDelayed(this, 100)
                }
            }
        }

        firstUpBtn.setOnClickListener {
            addDigit(firstDigit)
        }

        firstUpBtn.setOnLongClickListener {
            currentDigitTextView = firstDigit
            isIncreasing = true
            handler.post(increaseDigitRunnable)
            true
        }

        firstUpBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isIncreasing = false
            }
            false
        }

        firstDownBtn.setOnClickListener {
            subtractDigit(firstDigit)
        }

        firstDownBtn.setOnLongClickListener {
            currentDigitTextView = firstDigit
            isDecreasing = true
            handler.post(decreaseDigitRunnable)
            true
        }

        firstDownBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isDecreasing = false
            }
            false
        }



        secondUpBtn.setOnClickListener {
            addDigit(secondDigit)
        }

        secondUpBtn.setOnLongClickListener {
            currentDigitTextView = secondDigit
            isIncreasing = true
            handler.post(increaseDigitRunnable)
            true
        }

        secondUpBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isIncreasing = false
            }
            false
        }

        secondDownBtn.setOnClickListener {
            subtractDigit(secondDigit)
        }

        secondDownBtn.setOnLongClickListener {
            currentDigitTextView = secondDigit
            isDecreasing = true
            handler.post(decreaseDigitRunnable)
            true
        }

        secondDownBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isDecreasing = false
            }
            false
        }



        thirdUpBtn.setOnClickListener {
            addDigit(thirdDigit)
        }

        thirdUpBtn.setOnLongClickListener {
            currentDigitTextView = thirdDigit
            isIncreasing = true
            handler.post(increaseDigitRunnable)
            true
        }

        thirdUpBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isIncreasing = false
            }
            false
        }

        thirdDownBtn.setOnClickListener {
            subtractDigit(thirdDigit)
        }

        thirdDownBtn.setOnLongClickListener {
            currentDigitTextView = thirdDigit
            isDecreasing = true
            handler.post(decreaseDigitRunnable)
            true
        }

        thirdDownBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isDecreasing = false
            }
            false
        }



        fourthUpBtn.setOnClickListener {
            addDigit(fourthDigit)
        }

        fourthUpBtn.setOnLongClickListener {
            currentDigitTextView = fourthDigit
            isIncreasing = true
            handler.post(increaseDigitRunnable)
            true
        }

        fourthUpBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isIncreasing = false
            }
            false
        }

        fourthDownBtn.setOnClickListener {
            subtractDigit(fourthDigit)
        }

        fourthDownBtn.setOnLongClickListener {
            currentDigitTextView = fourthDigit
            isDecreasing = true
            handler.post(decreaseDigitRunnable)
            true
        }

        fourthDownBtn.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                isDecreasing = false
            }
            false
        }



        resetBtn.setOnClickListener {
            firstDigit.text = "2"
            secondDigit.text = "5"
            thirdDigit.text = "0"
            fourthDigit.text = "0"
        }

        saveBtn.setOnClickListener {
            saveSettings()
        }

        backBtn.setOnClickListener {
            childFragmentManager.popBackStack()
        }
    }


    private fun addDigit(digit: TextView) {
        var currentDigit: Int = digit.text.toString().toInt()
        if (digit.id == R.id.firstDigit || digit.id == R.id.thirdDigit) {
            if (currentDigit < 5) {
                val newDigit = currentDigit + 1
                digit.text = newDigit.toString()
            }
        } else if (digit.id == R.id.secondDigit || digit.id == R.id.fourthDigit) {
            if (currentDigit < 9) {
                val newDigit = currentDigit + 1
                digit.text = newDigit.toString()
            }
        }
    }


    private fun subtractDigit(digit: TextView) {
        val currentDigit: Int = digit.text.toString().toInt()
        if (currentDigit > 0) {
            val newDigit = currentDigit - 1
            digit.text = newDigit.toString()
        }
    }


    private fun saveSettings() {
        val firstNum = firstDigit.text.toString()
        val secondNum = secondDigit.text.toString()
        val minutesStr = "$firstNum$secondNum"

        val thirdNum = thirdDigit.text.toString()
        val fourthNum = fourthDigit.text.toString()
        val secondsStr = "$thirdNum$fourthNum"

        val minutes = minutesStr.toLong()
        val seconds = secondsStr.toLong()

        if (minutes > 0 || seconds > 0) {
            pmdr.savePomodoro(
                requireContext(), minutes, seconds, firstNum,
                secondNum, thirdNum, fourthNum
            )
            Toast.makeText(requireContext(), "Reset timer to see changes", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Please enter valid minutes and seconds", Toast.LENGTH_LONG).show()
        }
    }
}

