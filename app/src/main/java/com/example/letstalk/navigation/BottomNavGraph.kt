package com.example.letstalk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.letstalk.screens.ConversationScreen
import com.example.letstalk.screens.SettingsScreen
import com.example.letstalk.screens.TextTranslationScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
){
   NavHost(
       navController = navController ,
       startDestination = BottomBarScreen.TextTranslation.route,
   ){
       composable(route = BottomBarScreen.TextTranslation.route){
           TextTranslationScreen()
       }
       composable(route = BottomBarScreen.Conversation.route){
           ConversationScreen()
       }
       composable(route = BottomBarScreen.Settings.route){
           SettingsScreen()
       }
   }
}