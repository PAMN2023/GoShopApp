package com.example.goshopapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.HomePageDataCallback
import com.example.goshopapp.domain.model.HomePageData

@Composable
fun HomeScreen(navController: NavHostController) {
    val firestoreManager = FirebaseFirestoreManage()
    var homeData by remember { mutableStateOf<HomePageData?>(null) }

    DisposableEffect(Unit) {
        firestoreManager.getHomePageData(object : HomePageDataCallback {
            override fun onHomePageDataReceived(data: HomePageData) {
                homeData = data
            }
            override fun onHomePageDataError(error: Exception) {
                Log.d("Error", "Error al obtener datos: $error")
            }
        })

        onDispose { }
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        homeData?.let { HomeContent(it, navController) }
    }
}
