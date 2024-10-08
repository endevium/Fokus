package com.example.fokus.api

import android.content.Context
import android.content.SharedPreferences

class loadLongbreakSettings {
    fun loadFirstDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("firstDigitLB", null)
    }

    fun loadSecondDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("secondDigitLB", null)
    }

    fun loadThirdDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("thirdDigitLB", null)
    }

    fun loadFourthDigit(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("fourthDigitLB", null)
    }
}