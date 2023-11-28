package com.example.goshopapp.presentation.screens.actionpopups

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun CreateListScreen() {
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()
    val userId = authManager.getCurrentUserId()
    val listName = remember { mutableStateOf("") }
    val listImg = remember { mutableStateOf("") }
    val textFieldsColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        containerColor = Color(android.graphics.Color.parseColor("#D3D3D3")))
    Column(
        modifier = Modifier
            .height(250.dp)
            .width(350.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Creación de una lista de productos",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(text = "NOMBRE DE LA LISTA",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp, end = 24.dp, bottom = 10.dp)
        )
        TextField(
            value = listName.value,
            onValueChange = { newValue ->
                listName.value = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            placeholder = {
                Text(
                    text = "Introduce el nombre de la lista",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 1.dp)
                )
            },
            maxLines = 1,
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
        )
        Text(text = "IMÁGEN DE LA LISTA",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp, end = 24.dp, bottom = 10.dp)
        )
        TextField(
            value = listName.value,
            onValueChange = { newValue ->
                listName.value = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            placeholder = {
                Text(
                    text = "Introduce la url de la imágen de la lista",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 1.dp)
                )
            },
            maxLines = 1,
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
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
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = {
                    storeManager.createUserList(userId!!, listName.value, listImg.value)
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#007562")),
                    contentColor = Color(android.graphics.Color.parseColor("#007562"))
                )
            ) {
                Text(text = "CONFIRMAR",
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateListPreview() {
    CreateListScreen()
}