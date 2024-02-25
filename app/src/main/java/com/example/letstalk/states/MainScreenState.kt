package com.example.letstalk.states

data class MainScreenState(
    val buttonEnabled: Boolean = true,
    val inputtedText: String = "",
    val translatedText: String = "",
    val recordedText: String? = null

    )
