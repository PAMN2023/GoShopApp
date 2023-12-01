package com.example.goshopapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.presentation.screens.expenses.ExpensesScreen
import com.example.goshopapp.presentation.screens.favourites.FavouritesScreen
import com.example.goshopapp.presentation.screens.history.HistoryScreen
import com.example.goshopapp.presentation.screens.home.HomeScreen
import com.example.goshopapp.presentation.screens.listdetails.ListDetailsScreen
import com.example.goshopapp.presentation.screens.login.LoginScreen
import com.example.goshopapp.presentation.screens.product.ProductDetailsScreen
import com.example.goshopapp.presentation.screens.profile.ProfileScreen
import com.example.goshopapp.presentation.screens.register.RegisterScreen
import com.example.goshopapp.presentation.screens.scanner.ScannerScreen
import com.example.goshopapp.presentation.screens.splash.SplashScreen
import com.example.goshopapp.presentation.screens.userlists.UserListsScreen
import com.example.goshopapp.presentation.viewmodel.ListDetailsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    val authManager = FirebaseAuth()
    val listDetailsViewModel = viewModel<ListDetailsViewModel>()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController)
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
            if (authManager.getCurrentUserId() == null) {
                navController.popBackStack(AppScreens.HomeScreen.route, inclusive = false)
                navController.navigate(LateralScreens.LoginScreen.route)
            } else {
                UserListsScreen(navController, listDetailsViewModel)
            }
        }
        composable(LateralScreens.ExpensesScreen.route) {
            if (authManager.getCurrentUserId() == null) {
                navController.popBackStack(AppScreens.HomeScreen.route, inclusive = false)
                navController.navigate(LateralScreens.LoginScreen.route)
            } else {
                ExpensesScreen()
            }
        }
        composable(LateralScreens.FavouritesScreen.route) {
            if (authManager.getCurrentUserId() == null) {
                navController.popBackStack(AppScreens.HomeScreen.route, inclusive = false)
                navController.navigate(LateralScreens.LoginScreen.route)
            } else {
                FavouritesScreen()
            }
        }
        composable(LateralScreens.HistoryScreen.route) {
            if (authManager.getCurrentUserId() == null) {
                navController.popBackStack(AppScreens.HomeScreen.route, inclusive = false)
                navController.navigate(LateralScreens.LoginScreen.route)
            } else {
                HistoryScreen()
            }
        }
        composable(AppScreens.ProductDetailsScreen.route
                + "/{productName}"
                + "/{productImage}"
                + "/{productDescription}"
                + "/{productInformation}"
                + "/{productPrice}",
            arguments = listOf(
                navArgument(name = "productName") {
                type = NavType.StringType
                },
                navArgument(name = "productImage") {
                    type = NavType.StringType
                },
                navArgument(name = "productDescription") {
                    type = NavType.StringType
                },
                navArgument(name = "productInformation") {
                    type = NavType.StringType
                },
                navArgument(name = "productPrice") {
                    type = NavType.StringType
                }
            )
        ) {
            ProductDetailsScreen(
                it.arguments?.getString("productName"),
                it.arguments?.getString("productImage"),
                it.arguments?.getString("productDescription"),
                it.arguments?.getString("productInformation"),
                it.arguments?.getString("productPrice")
            )
        }
        composable(AppScreens.ListDetailsScreen.route) {
            if (authManager.getCurrentUserId() == null) {
                navController.popBackStack(AppScreens.HomeScreen.route, inclusive = false)
                navController.navigate(LateralScreens.LoginScreen.route)
            } else {
                ListDetailsScreen(listDetailsViewModel)
            }
        }
    }
}

