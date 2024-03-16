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
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.letstalk.contract.RecognitionContract
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun TextTranslationScreen(
    viewModel: MainViewModel = viewModel()
){
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )
    val recognitionLauncher = rememberLauncherForActivityResult(
        contract = RecognitionContract(),
        onResult ={
            viewModel.changeTextValue(it.toString())
        }
    )
    val state = viewModel.state.value
    val context = LocalContext.current

    SideEffect {
        permissionState.launchPermissionRequest()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color.LightGray.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(15.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(15.dp))
                    .background(Color.White)
            ) {
                TextField(
                    value = state.inputtedText,
                    onValueChange = { newValue -> viewModel.onInputtedText(newValue) },
                    placeholder = { Text(text = "Enter Text Here", color = Color.LightGray)},
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                )

                Button(
                    onClick = {
                        viewModel.onButtonClick(
                            text = state.inputtedText,
                            context = context
                        )
                    },
                    enabled = state.buttonEnabled,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)

                ) {
                    Text(text = "Translate")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(15.dp))
                    .background(Color.White)
            ) {
                Text(
                    text = state.translatedText,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                )

                Button(
                    onClick = {
                        viewModel.textToSpeech(context, state.translatedText)
                    },
                    enabled = state.buttonEnabled,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ){
                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Speak")
                }
            }
        }
    }
}
