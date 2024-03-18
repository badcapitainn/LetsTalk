package com.example.letstalk.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    val context = LocalContext.current
    val state = viewModel.state.value
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
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(2.dp, Color.Gray, RoundedCornerShape(15.dp))
                    .background(Color.White)
            ){
                Box (
                    modifier = Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ){
                    if (viewModel.state.value.recordedText != null){
                        //to remove the square brackets
                        val originalString = viewModel.state.value.recordedText!!
                        val formattedString = originalString.replace(Regex("^\\[(.+)\\]$"), "$1")
                        //Text(
                        //text = formattedString,
                        //fontSize = 24.sp
                        //)
                        viewModel.onButtonClick(
                            text = formattedString,
                            context = context
                        )

                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = state.translatedText)

                    }

                }

            }
            
            Spacer(modifier = Modifier.height(20.dp))

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

    }

}
