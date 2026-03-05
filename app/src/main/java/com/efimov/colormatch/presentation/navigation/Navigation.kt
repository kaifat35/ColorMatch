package com.efimov.colormatch.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.efimov.colormatch.presentation.screens.CameraScreen
import com.efimov.colormatch.presentation.screens.HistoryScreen
import com.efimov.colormatch.presentation.screens.ResultScreen
import com.efimov.colormatch.presentation.viewmodel.ColorViewModel

sealed class Screen(val route: String) {
    object Camera : Screen("camera")
    object Result : Screen("result")
    object History : Screen("history")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ColorViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.Camera.route) {
        composable(Screen.Camera.route) {
            CameraScreen(navController, viewModel)
        }
        composable(Screen.Result.route) {
            ResultScreen(navController, viewModel)
        }
        composable(Screen.History.route) {
            HistoryScreen(navController, viewModel)
        }
    }
}