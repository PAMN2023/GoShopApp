package com.example.goshopapp.presentation.screens.userlists

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.goshopapp.R
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.Lists
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.AsyncImage

@SuppressLint("MutableCollectionMutableState")
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
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "MIS LISTAS",
                    color = Color(android.graphics.Color.parseColor("#007562")),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            userLists?.let { lists ->
                items(lists) { list ->
                    Log.d("LISTA", list.toString())
                    Row(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .then(Modifier.background(Color(0XFF5FCEBC), RoundedCornerShape(8.dp)))
                            .clickable {  },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (list.image.isNotEmpty()) {
                            AsyncImage(
                                model = list.image,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(50.dp),
                                contentScale = ContentScale.Fit,
                                contentDescription = null
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(IntrinsicSize.Max)
                                .padding(10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = list.name,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Precio aproximado: " + list.aproxPrice + "€",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        if (list.shared) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_share_friend),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)
                                    .offset(x = 10.dp, y = (-15).dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
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

//@Preview(showBackground = true)
//@Composable
//fun UserListsPreview() {
//    val navController = rememberNavController()
//    UserListsScreen(navController)
//}