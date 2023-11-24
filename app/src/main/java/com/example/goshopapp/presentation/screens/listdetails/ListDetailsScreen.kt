package com.example.goshopapp.presentation.screens.listdetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.goshopapp.presentation.viewmodel.ListDetailsViewModel

@Composable
fun ListDetailsScreen(listDetailsViewModel: ListDetailsViewModel) {

    val items = listDetailsViewModel.items
    Log.d("LISTAAA", items.toString())

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Productos en VistaDestino:", fontWeight = FontWeight.Bold)
        for (product in items) {
            Text("${product.name} - ${product.price}")
        }
    }
}