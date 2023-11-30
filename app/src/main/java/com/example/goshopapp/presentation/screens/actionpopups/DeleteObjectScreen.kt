package com.example.goshopapp.presentation.screens.actionpopups

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.domain.model.Product

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DeleteObjectScreen(deleteItem: Boolean, userList: Lists, item: Product? = null): Boolean {
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()
    val userId = authManager.getCurrentUserId()
    var result by remember { mutableStateOf(true) }
    if (deleteItem) {
        Column(
            modifier = Modifier
                .height(150.dp)
                .width(350.dp)
                .background(Color(0xefffffff)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
            text = "¿Esta seguro de querer\neliminar "+item!!.name+" de\n la lista "+userList.name+"?",
            color = Color.Black,
            fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        result = false
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
                Spacer(modifier = Modifier.width(24.dp))
                Button(
                    onClick = {
                        storeManager.deleteItemOfUserList(userId!!,userList.name,item.name)
                        result = false
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
    } else {
        Column(
            modifier = Modifier
                .height(150.dp)
                .width(350.dp)
                .background(Color(0xefffffff)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Esta seguro de querer\neliminar la lista " + userList.name + "?",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        result = false
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
                Spacer(modifier = Modifier.width(24.dp))
                Button(
                    onClick = {
                        storeManager.deleteUserList(userId!!,userList.name)
                        result = false
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
    return result
}

@Preview(showBackground = true)
@Composable
fun DeleteObjectPreviw() {
    val almendras = Product("Almendras", "0.5", "")
    DeleteObjectScreen(true,
        Lists("Prueba",false,"0.5","", mutableListOf(almendras)),almendras)
}