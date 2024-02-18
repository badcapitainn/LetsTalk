package com.example.letstalk.viewModels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.letstalk.states.MainScreenState

class MainViewModel: ViewModel() {
    private val screenState = mutableStateOf(
        MainScreenState()
    )
    val state:State<MainScreenState> = screenState

    //function that will update the dataclass when value in text field is changed
    fun onInputtedText(
        text: String
    ){
        screenState.value = state.value.copy(
            inputtedText = text
        )
    }

    //function to handle clicked button which will hold the translation logic
    fun onButtonClick(
        text: String,
        context: Context
    ){

    }
}