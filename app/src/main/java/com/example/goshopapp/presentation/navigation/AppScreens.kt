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
    object RegisterScreen: AppScreens("register_screen", "Register")
}

/*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object SplashScreen: AppScreens("splash_screen")
    object HomeScreen: AppScreens("home_screen", "Home Screen", Icons.Outlined.Home)
    object ScannerScreen: AppScreens("scanner_screen", "Scanner Screen", Icons.Outlined.CameraAlt)
    object ProfileScreen: AppScreens("profile_screen", "Profile Screen", Icons.Outlined.AccountCircle)
}
*/