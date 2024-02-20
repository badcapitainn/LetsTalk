package com.example.letstalk.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.letstalk.viewModels.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.inputtedText,
            onValueChange = { newValue -> viewModel.onInputtedText(newValue) },
            shape = RoundedCornerShape(50.dp),

        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            viewModel.onButtonClick(
                text = state.inputtedText,
                context = context
            )
        },
            enabled = state.buttonEnabled
        ) {

            Text(text = "Translate")

        }
        Spacer(modifier = Modifier.height(7.dp))
        
        Text(text = state.translatedText)
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(onClick = {
            viewModel.textToSpeech(context, state.translatedText)
        },
            enabled = state.buttonEnabled
        ){
            Text(text = "Speak")

        }
    }

}
