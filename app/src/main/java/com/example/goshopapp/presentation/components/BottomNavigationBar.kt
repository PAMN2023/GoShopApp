package com.example.goshopapp.presentation.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.goshopapp.R
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.navigation.LateralScreens

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<AppScreens>
) {
    // Keep track of whether the bottom navigation bar should be visible
    val isBottomNavigationVisible = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        // Hide the bottom navigation bar initially
        isBottomNavigationVisible.value = false

        // Show the bottom navigation bar when the current destination is the home screen
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isBottomNavigationVisible.value = destination.route != LateralScreens.LoginScreen.route &&
                    destination.route != LateralScreens.RegisterScreen.route &&
                    destination.route != AppScreens.SplashScreen.route
        }
    }

    if (isBottomNavigationVisible.value) {
        BottomAppBar {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            if (item.icon != null) {
                                Icon(painter = painterResource(item.icon), contentDescription = item.title)
                            } else {
                                // No icon provided, use a placeholder instead
                                Icon(painter = painterResource(R.drawable.place_holder), contentDescription = item.title)
                            }
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}
