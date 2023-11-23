package com.example.goshopapp.presentation.screens.userlists

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.R
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.UserDataCallback
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.navigation.LateralScreens

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListsScreen(navController: NavHostController) {
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
        Text(text = "MIS LISTAS", color = Color(android.graphics.Color.parseColor("#007562")), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        userLists?.forEach{ userList ->
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    //Navegación a detalles de lista
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#00B697")),
                    contentColor = Color(android.graphics.Color.parseColor("#00B697"))
                )
            ) {
                Row (
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = userList.name, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Precio aproximado: "+userList.aproxPrice+"€", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    if (userList.shared) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_share_friend),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
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