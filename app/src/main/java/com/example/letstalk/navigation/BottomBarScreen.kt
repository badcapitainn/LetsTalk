package com.example.letstalk.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Main: BottomBarScreen(
        route = "main",
        title = "Main",
        icon = Icons.Filled.ChatBubble
    )

    object Conversation: BottomBarScreen(
        route = "conversation",
        title = "Conversation",
        icon = Icons.Filled.ChatBubble
    )

    object Settings: BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Filled.Settings
    )
}

