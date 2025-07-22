package com.example.retask.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.retask.ui.screen.MainScreen

@Composable
fun NavigationComponent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = User,
        modifier = modifier
    ) {
        navigation<User>(startDestination = Main) {
            composable<Main> {
                MainScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}