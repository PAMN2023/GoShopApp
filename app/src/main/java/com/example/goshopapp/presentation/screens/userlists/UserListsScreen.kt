package com.example.goshopapp.presentation.screens.userlists

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.UserDataCallback
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.presentation.navigation.AppScreens

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListsScreen(navController: NavHostController) {
    val userId = userId()
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()

    var userLists by remember { mutableStateOf<MutableList<Lists>?>(null) }

    DisposableEffect(Unit) {
        authManager.getCurrentUserId()?.let {
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Listas del usuario")
        Text(text = userId)
        Text(text = userLists.toString())
//        for (list in userLists) {
//            Button(
//                onClick = {
//                    //Implementar navegacion a la vista de la lista
//                },
//                shape = MaterialTheme.shapes.medium,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(android.graphics.Color.parseColor("#007562")),
//                    contentColor = Color(android.graphics.Color.parseColor("#007562"))
//                )
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(text = list.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = list.shared.toString(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                }
//            }
//        }
    }
}

private fun userId(): String {
    val authManager = FirebaseAuth()
    if (authManager.getCurrentUserId() == null || authManager.getCurrentUserId() == "") {
        return ""
    } else {
        return authManager.getCurrentUserId()!!
    }
}

@Preview(showBackground = true)
@Composable
fun UserListsPreview() {
    val navController = rememberNavController()
    UserListsScreen(navController)
}