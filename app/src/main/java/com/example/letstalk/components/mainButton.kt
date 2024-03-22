package com.example.letstalk.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun recordButton( onClick:() -> Unit ) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(90.dp),

    ) {
        Icon(
            Icons.Filled.Mic,
            contentDescription = "Microphone",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.surfaceTint
        )
    }
}