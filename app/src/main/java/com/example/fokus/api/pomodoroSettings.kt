package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

class pomodoroSettings {
    fun savePomodoro(context: Context, minutes: Long, seconds: Long, firstDigit: String,
                     secondDigit: String, thirdDigit: String, fourthDigit: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("pomodoro_minutes", minutes)
        editor.putLong("pomodoro_seconds", seconds)

        editor.putString("firstDigitP", firstDigit)
        editor.putString("secondDigitP", secondDigit)
        editor.putString("thirdDigitP", thirdDigit)
        editor.putString("fourthDigitP", fourthDigit)

        editor.apply()
    }

    fun pomodoroMinutes(context: Context): Long? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getLong("pomodoro_minutes", 25)
    }

    fun pomodoroSeconds(context: Context): Long? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getLong("pomodoro_seconds", 0)
    }
}