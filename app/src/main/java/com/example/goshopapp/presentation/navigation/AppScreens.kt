package com.example.goshopapp.presentation.navigation

import com.example.goshopapp.R

sealed class AppScreens(
    val route: String,
    val title: String? = null,
    val icon: Int? = null
) {
    object SplashScreen: AppScreens("splash_screen")
    object HomeScreen: AppScreens("home_screen", "Home", R.drawable.ic_home)
    object ScannerScreen: AppScreens("scanner_screen", "Scanner", R.drawable.ic_bar_code)
    object ProfileScreen: AppScreens("profile_screen", "Profile", R.drawable.ic_profile)
    object ProductDetailsScreen: AppScreens("product_screen")
}
