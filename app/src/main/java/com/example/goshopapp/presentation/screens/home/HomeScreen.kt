package com.example.goshopapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.HomePageDataCallback
import com.example.goshopapp.domain.model.HomePageData

@Composable
fun HomeScreen() {
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

    homeData?.let { data ->
        // Mostrar los nombres de los productos
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(data.products) { product ->
                Text(text = product.name)

                AsyncImage(
                    model = product.image,
                    contentDescription = "Translated description of what the image contains",
                )
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}*/
