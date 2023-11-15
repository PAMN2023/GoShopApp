package com.example.goshopapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.goshopapp.R
import com.example.goshopapp.presentation.navigation.AppScreens

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
            if (destination.route == AppScreens.HomeScreen.route) {
                isBottomNavigationVisible.value = true
            }
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
                        //label = { item.title?.let { Text(it) } },
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

/*
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<AppScreens>
) {
    BottomAppBar {
        NavigationBar {
            items.forEach { item->
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }

                            launchSingleTop = true
                        }
                    },
                    icon = {
                        item.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title
                            )
                        }
                    },
                    label = { item.title?.let { Text(it) } }
                )
            }
        }
    }
}
*/