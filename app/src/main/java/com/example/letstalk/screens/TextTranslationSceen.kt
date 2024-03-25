package com.example.letstalk.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import com.example.letstalk.contract.RecognitionContract
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
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

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(15.dp))

            ) {
                BasicTextField(
                    value = state.inputtedText,
                    onValueChange = { newValue -> viewModel.onInputtedText(newValue) },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart),
                    decorationBox = { innerTextField ->
                        if (state.inputtedText.isEmpty()) {
                            Text(text = "Enter Text Here", color = Color.LightGray)
                        }
                        innerTextField()
                    }
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

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, MaterialTheme.colorScheme.surfaceTint, RoundedCornerShape(15.dp))

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
