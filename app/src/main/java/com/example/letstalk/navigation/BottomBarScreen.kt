package com.example.letstalk.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object TextTranslation: BottomBarScreen(
        route = "textTranslation",
        title = "Text",
        icon = Icons.Default.ChatBubble
    )

    object Conversation: BottomBarScreen(
        route = "conversation",
        title = "Conversation",
        icon = Icons.Default.Mic
    )

    object Settings: BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}

