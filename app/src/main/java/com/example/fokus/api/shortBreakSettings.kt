package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

class shortBreakSettings {
    fun saveShortbreak(context: Context, minutes: Long, seconds: Long, firstDigit: String,
                       secondDigit: String, thirdDigit: String, fourthDigit: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("shortbreak_minutes", minutes)
        editor.putLong("shortbreak_seconds", seconds)

        editor.putString("firstDigitSB", firstDigit)
        editor.putString("secondDigitSB", secondDigit)
        editor.putString("thirdDigitSB", thirdDigit)
        editor.putString("fourthDigitSB", fourthDigit)

        editor.apply()
    }

    fun shortbreakMinutes(context: Context): Long? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getLong("shortbreak_minutes", 5)
    }

    fun shortbreakSeconds(context: Context): Long? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getLong("shortbreak_seconds", 0)
    }
}