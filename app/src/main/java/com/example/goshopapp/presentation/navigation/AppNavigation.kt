package com.example.goshopapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.presentation.screens.expenses.ExpensesScreen
import com.example.goshopapp.presentation.screens.favourites.FavouritesScreen
import com.example.goshopapp.presentation.screens.history.HistoryScreen
import com.example.goshopapp.presentation.screens.home.HomeScreen
import com.example.goshopapp.presentation.screens.lists.ListsScreen
import com.example.goshopapp.presentation.screens.login.LoginScreen
import com.example.goshopapp.presentation.screens.profile.ProfileScreen
import com.example.goshopapp.presentation.screens.register.RegisterScreen
import com.example.goshopapp.presentation.screens.scanner.ScannerScreen
import com.example.goshopapp.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    val authManager = FirebaseAuth()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route,
         enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
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
            if (authManager.getCurrentUserId() == null) {
                navController.popBackStack(AppScreens.HomeScreen.route, inclusive = false)
                navController.navigate(LateralScreens.LoginScreen.route)
            } else {
                ProfileScreen(navController)
            }
        }
        composable(LateralScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(LateralScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(LateralScreens.ListsScreen.route) {
            ListsScreen()
        }
        composable(LateralScreens.ExpensesScreen.route) {
            ExpensesScreen()
        }
        composable(LateralScreens.FavouritesScreen.route) {
            FavouritesScreen()
        }
        composable(LateralScreens.HistoryScreen.route) {
            HistoryScreen()
        }
    }
}
