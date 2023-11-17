package com.example.goshopapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.goshopapp.presentation.navigation.LateralScreens.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LateralMenu(
    navController: NavHostController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val menu_items = listOf(
        ListsScreen,
        ExpensesScreen,
        FavouritesScreen,
        HistoryScreen
    )
    val auth_items = listOf(
        LoginScreen,
        RegisterScreen
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(modifier = Modifier
                    .background(Color(0XFF00B697))
                    .fillMaxHeight()
                    //.fillMaxWidth(),
                    .width(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "GO SHOP",
                        color = Color(0xFFC9511D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(24.dp)
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    auth_items.forEach { item ->
                        Text(
                            text = item.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable(onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(item.route)
                                })
                        )
                    }

                    Spacer(modifier = Modifier.height(75.dp))

                    menu_items.forEach {item->
                        Text(
                            text = item.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable(onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(item.route)
                                })
                        )
                    }
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                content()
            }
        }
    }

    /*CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            modifier = Modifier.fillMaxSize(),
            drawerContent = {
                ModalDrawerSheet (
                    drawerContainerColor=Color(0XFF00B697),
                ){
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Center
                    ) {
                        menu_items.forEach {item->
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .padding(10.dp),
                                label = { Text(
                                    modifier = Modifier.fillMaxSize()
                                        .align(Alignment.CenterHorizontally)
                                        .background(Color.Red),
                                    text = item.title) },
                                //selected = navController.currentBackStackEntryAsState().value?.destination?.route == item.route,
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(item.route)
                                }
                            )
                        }
                    }
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                content()
            }
        }
    }*/
}