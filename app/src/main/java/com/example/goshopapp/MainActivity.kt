@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.goshopapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.presentation.components.BottomNavigationBar
import com.example.goshopapp.presentation.components.LateralMenu
import com.example.goshopapp.presentation.components.TopBar
import com.example.goshopapp.presentation.navigation.AppNavigation
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.navigation.AppScreens.*
import com.example.goshopapp.presentation.ui.theme.GoShopAppTheme
import com.example.goshopapp.presentation.viewmodel.ListDetailsViewModel

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

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val listDetailsViewModel = viewModel<ListDetailsViewModel>()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    
    val navigationItems = listOf(
        ScannerScreen,
        HomeScreen,
        ProfileScreen
    )

    LateralMenu(
        navController = navController,
        drawerState = drawerState,
        listDetailsViewModel = listDetailsViewModel
    ) {
        Content(
            navController = navController,
            navigationItems =navigationItems,
            drawerState = drawerState,
            listDetailsViewModel
        )
    }
}

@Composable
fun Content(
    navController: NavHostController,
    navigationItems: List<AppScreens>,
    drawerState: DrawerState,
    listDetailsViewModel: ListDetailsViewModel
){
    Scaffold(
        topBar = {
                 TopBar(navController,drawerState)
        },
        bottomBar = { BottomNavigationBar(navController = navController, items = navigationItems) }
    ) {padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ){
            AppNavigation(navController,listDetailsViewModel)
        }
    }
}
