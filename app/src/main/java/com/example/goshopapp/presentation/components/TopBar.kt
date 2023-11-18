package com.example.goshopapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.goshopapp.presentation.navigation.AppScreens
import kotlinx.coroutines.launch
import com.example.goshopapp.R
import com.example.goshopapp.presentation.navigation.LateralScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val isTopBarVisible = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        // Hide the Top Bar initially
        isTopBarVisible.value = false

        // Show the Top Bar when the current destination is the home screen
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isTopBarVisible.value = destination.route != LateralScreens.LoginScreen.route &&
                    destination.route != LateralScreens.RegisterScreen.route &&
                    destination.route != AppScreens.SplashScreen.route
        }
    }

    if (isTopBarVisible.value) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_header),
                contentDescription = "GoShop Logo",
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .align(Alignment.CenterVertically)
            )

            val tint: Color = Color(0XFF007562)
            CompositionLocalProvider(LocalContentColor provides tint) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    //painter = painterResource(R.drawable.ic_menu),
                    contentDescription = "Top Bar Menu",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp, 40.dp)
                        .clickable(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        })
                )
            }

        }
    }
}
