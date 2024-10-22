package com.example.fokus


import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fokus.api.loadShortbreakSettings
import com.example.fokus.api.shortBreakSettings


class ChangeSBTimerFragment : Fragment(R.layout.fragment_changesbtimer) {
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
    private val shrtbrk = shortBreakSettings()


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


        val settings = loadShortbreakSettings()


        if (settings.loadFirstDigit(requireContext()) != null &&
            settings.loadSecondDigit(requireContext()) != null &&
            settings.loadThirdDigit(requireContext()) != null &&
            settings.loadFourthDigit(requireContext()) != null) {


            firstDigit.text = settings.loadFirstDigit(requireContext())
            secondDigit.text = settings.loadSecondDigit(requireContext())
            thirdDigit.text = settings.loadThirdDigit(requireContext())
            fourthDigit.text = settings.loadFourthDigit(requireContext())
        }


        firstUpBtn.setOnClickListener {
            addDigit(firstDigit)
        }


        firstDownBtn.setOnClickListener {
            subtractDigit(firstDigit)
        }


        secondUpBtn.setOnClickListener {
            addDigit(secondDigit)
        }


        secondDownBtn.setOnClickListener {
            subtractDigit(secondDigit)
        }


        thirdUpBtn.setOnClickListener {
            addDigit(thirdDigit)
        }


        thirdDownBtn.setOnClickListener {
            subtractDigit(thirdDigit)
        }


        fourthUpBtn.setOnClickListener {
            addDigit(fourthDigit)
        }


        fourthDownBtn.setOnClickListener {
            subtractDigit(fourthDigit)
        }


        resetBtn.setOnClickListener {
            firstDigit.text = "0"
            secondDigit.text = "5"
            thirdDigit.text = "0"
            fourthDigit.text = "0"
        }


        saveBtn.setOnClickListener {
            saveSettings()
        }


        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun addDigit(digit: TextView) {
        var currentDigit: Int = digit.text.toString().toInt()
        if (digit.id == R.id.firstDigit || digit.id == R.id.thirdDigit) {
            if (currentDigit < 5) {
                var newDigit = currentDigit + 1
                digit.text = newDigit.toString()
            }
        } else if (digit.id == R.id.secondDigit || digit.id == R.id.fourthDigit) {
            if (currentDigit < 9) {
                var newDigit = currentDigit + 1
                digit.text = newDigit.toString()
            }
        }
    }


    private fun subtractDigit(digit: TextView) {
        var currentDigit: Int = digit.text.toString().toInt()
        if (currentDigit > 0) {
            var newDigit = currentDigit - 1
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


        shrtbrk.saveShortbreak(requireContext(), minutesStr.toLong(), secondsStr.toLong(), firstNum,
            secondNum, thirdNum, fourthNum)
        Toast.makeText(requireContext(), "Reset timer to see changes", Toast.LENGTH_LONG).show()
    }
}

