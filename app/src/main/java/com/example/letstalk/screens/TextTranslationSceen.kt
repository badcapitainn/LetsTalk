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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.letstalk.components.recordButton
import com.example.letstalk.contract.RecognitionContract
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
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
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.inputtedText,
                    onValueChange = { newValue -> viewModel.onInputtedText(newValue) },
                    label = { Text("Enter text here") },
                    shape = RoundedCornerShape(50.dp)
                )

            }

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

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.LightGray
            ) {
                Text(text = state.translatedText)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                viewModel.textToSpeech(context, state.translatedText)
            },
                enabled = state.buttonEnabled
            ){
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Speak")
            }
        }
    }
}
