package com.example.goshopapp.presentation.screens.actionpopups

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.domain.model.Product

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DeleteObjectScreen(deleteItem: Boolean, item: Product, userList: Lists) {
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()
    val userId = authManager.getCurrentUserId()
    if (deleteItem) {
        Text(
            text = "¿Esta seguro de querer\neliminar "+item.name+" de\n la lista "+userList.name+"?",
            color = Color.Black,
            fontWeight = FontWeight.Bold
            )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
            Button(
                onClick = {
                    storeManager.deleteItemOfUserList(userId!!,userList.name,item.name)
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#007562")),
                    contentColor = Color(android.graphics.Color.parseColor("#007562"))
                )
            ) {
                Text(text = "CONFIRMAR",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.Bold)
            }
        }
    } else {
        Text(
            text = "¿Esta seguro de querer\neliminar la lista " + userList.name + "?",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
                Text(
                    text = "CANCELAR",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = {
                    storeManager.deleteUserList(userId!!,userList.name)
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#007562")),
                    contentColor = Color(android.graphics.Color.parseColor("#007562"))
                )
            ) {
                Text(text = "CONFIRMAR",
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteObjectPreviw() {
    val almendras = Product("Almendras", "0.5", "")
    DeleteObjectScreen(true,
        almendras,
        Lists("Prueba",false,"0.5","", mutableListOf(almendras)))
}