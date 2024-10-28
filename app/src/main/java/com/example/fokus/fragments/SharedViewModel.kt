package com.example.fokus.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _textColor = MutableLiveData<Int>()
    private val _addColor = MutableLiveData<Int>()
    private val _backColor = MutableLiveData<Int>()
    private val _resetTimerEvent = MutableLiveData<Boolean>()
    private val _autoStart = MutableLiveData<Boolean>()

    val textColor: LiveData<Int> get() = _textColor
    val addColor: LiveData<Int> get() = _addColor
    val backColor: LiveData<Int> get() = _backColor
    val resetTimerEvent: LiveData<Boolean> get() = _resetTimerEvent
    val autoStart: LiveData<Boolean> get() = _autoStart

    fun setTextColor(color: Int) {
        _textColor.value = color
    }

    fun setAddColor(color: Int) {
        _addColor.value = color
    }

    fun setBackColor(color: Int) {
        _backColor.value = color
    }

    fun resetTimer() {
        _resetTimerEvent.value = true
    }

    fun setAutoStart(autoStart: Boolean) {
        _autoStart.value = autoStart
    }
}