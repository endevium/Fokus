package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

class loadPomodoroSettings {
    fun loadFirstDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("firstDigitP", null)
    }

    fun loadSecondDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("secondDigitP", null)
    }

    fun loadThirdDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("thirdDigitP", null)
    }

    fun loadFourthDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("fourthDigitP", null)
    }
}