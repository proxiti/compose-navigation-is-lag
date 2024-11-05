package com.chris.testanimation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SecondViewModel : ViewModel() {
    val textState = MutableStateFlow("test")

    init {
        viewModelScope.launch {
            delay(200)
            var collect = mutableListOf<String>()
            for (i in 1..5) {
                collect.add("kotlinx.coroutines.flow.MutableStateFlow")
            }
            textState.value = collect.toString()
        }
    }
}