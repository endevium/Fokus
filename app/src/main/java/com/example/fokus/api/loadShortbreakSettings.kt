package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

class loadShortbreakSettings {
    fun loadFirstDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("firstDigitSB", null)
    }

    fun loadSecondDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("secondDigitSB", null)
    }

    fun loadThirdDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("thirdDigitSB", null)
    }

    fun loadFourthDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("fourthDigitSB", null)
    }
}