@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.goshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.presentation.components.BottomNavigationBar
import com.example.goshopapp.presentation.navigation.AppNavigation
import com.example.goshopapp.presentation.navigation.AppScreens.*
import com.example.goshopapp.presentation.ui.theme.GoShopAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoShopAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        ScannerScreen,
        HomeScreen,
        ProfileScreen
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, items = navigationItems) }
    ) {padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ){
            AppNavigation(navController)
        }
    }
}