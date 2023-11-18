package com.example.goshopapp.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.goshopapp.R
import com.example.goshopapp.presentation.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        navController.navigate(AppScreens.HomeScreen.route)
    }
    Splash()
}

@Composable
fun Splash() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFF00B697)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_splash_screen),
            contentDescription ="Logo GoShopApp",
            Modifier.size(200.dp, 200.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}
