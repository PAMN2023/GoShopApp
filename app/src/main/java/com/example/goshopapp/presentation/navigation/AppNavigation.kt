package com.example.goshopapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.presentation.screens.home.HomeScreen
import com.example.goshopapp.presentation.screens.profile.ProfileScreen
import com.example.goshopapp.presentation.screens.scanner.ScannerScreen
import com.example.goshopapp.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen()
        }
        composable(AppScreens.ScannerScreen.route) {
            ScannerScreen()
        }
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}