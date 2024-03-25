package com.example.letstalk.components

import android.graphics.drawable.Icon
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IconButton(
    icon: (Icon) -> Unit,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier : Modifier
){
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
    ){
      icon
    }

}