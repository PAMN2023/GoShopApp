package com.example.goshopapp.presentation.screens.actionpopups

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.domain.model.Product

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AddItemToListScreen(product: Product) {
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()
    val userId = authManager.getCurrentUserId()
    var userLists by remember { mutableStateOf<MutableList<Lists>?>(null) }

    DisposableEffect(Unit) {
        userId?.let {
            storeManager.getIterableUserLists(it, object : UserListsCallback {
                override fun onUserListsReceived(data: MutableList<Lists>) {
                    userLists = data
                }
                override fun onUserDataError(error: Exception) {
                    Log.d("Error", "Error al obtener datos: $error")
                }
            })
        }
        onDispose { }
    }

    Column(
        modifier = Modifier
            .height(350.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "¿A que lista quiere\nagregar el producto?")
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            userLists?.let { lists ->
                items(lists) { list ->
                    Log.d("LISTA", list.toString())
                    Button(
                        onClick = {
                            storeManager.addItemToUserList(userId!!,list.name,product)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(android.graphics.Color.parseColor("#962B00")),
                            contentColor = Color(android.graphics.Color.parseColor("#962B00"))
                        )
                    ) {
                        Text(text = list.name, color = Color(0xFF007562), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    //Evento de cierre
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#962B00")),
                    contentColor = Color(android.graphics.Color.parseColor("#962B00"))
                )
            ) {
                Text(text = "CANCELAR",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddItemToListPreview() {
    AddItemToListScreen(Product("Almendras", "0.5", ""))
}
