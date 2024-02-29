package com.example.letstalk.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.letstalk.contract.RecognitionContract
import com.example.letstalk.viewModels.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ConversationScreen(
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
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        IconButton(onClick = {
            if (permissionState.status.isGranted){
                recognitionLauncher.launch(Unit)
            }else{
                permissionState.launchPermissionRequest()
            }
        }) {
            Icon(Icons.Default.Mic, contentDescription = "Record")
        }
    }
    if (viewModel.state.value.recordedText != null){
        androidx.compose.material3.Text(
            text = viewModel.state.value.recordedText!!,
            fontSize = 24.sp
        )
    }
}
