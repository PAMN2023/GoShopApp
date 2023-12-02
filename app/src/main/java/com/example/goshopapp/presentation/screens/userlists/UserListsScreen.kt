package com.example.goshopapp.presentation.screens.userlists

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.Lists
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.text.TextStyle
import coil.compose.AsyncImage
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.navigation.LateralScreens
import com.example.goshopapp.presentation.screens.actionpopups.CreateListScreen
import com.example.goshopapp.presentation.screens.actionpopups.DeleteObjectScreen
import com.example.goshopapp.presentation.viewmodel.ListDetailsViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun UserListsScreen(navController: NavHostController, listDetailsViewModel: ListDetailsViewModel) {
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()
    var userLists by remember { mutableStateOf<MutableList<Lists>?>(null) }
    var isCreatePopupVisible by remember { mutableStateOf(false) }
    var userList by remember { mutableStateOf<Lists?>(null) }
    fun toggleCreatePopupVisibility() {
        isCreatePopupVisible = !isCreatePopupVisible
    }
    var isDeletePopupVisible by remember { mutableStateOf(false) }
    fun toggleDeletePopupVisibility() {
        isDeletePopupVisible = !isDeletePopupVisible
    }
    DisposableEffect(Unit) {
        authManager.getCurrentUserId()?.let {
            storeManager.getIterableUserLists(it, object : UserListsCallback {
                override fun onUserListsReceived(data: MutableList<Lists>) {
                    val favIndex = data.indexOfFirst { it.name == "Favoritos" }
                    if (favIndex != -1) {
                        data.removeAt(favIndex)
                    }
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
            // TÍTULO DE LA VISTA
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
                    // FILA PARA CADA LISTA
                    Row(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .then(Modifier.background(Color(0XFF5FCEBC), RoundedCornerShape(8.dp)))
                            .clickable {
                                listDetailsViewModel.items.clear()
                                listDetailsViewModel.items.addAll(list.items)
                                listDetailsViewModel.listName = list.name
                                listDetailsViewModel.isShared = list.shared
                                navController.navigate(AppScreens.ListDetailsScreen.route)
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // IMAGEN DE LA LISTA
                        userList = list
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
                        // COLUMNA CON NOMBRE Y PRECIO APROXIMADO DE LA LISTA
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(200.dp)
                                .padding(10.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = list.name,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Precio aproximado: " + list.aproxPrice + "€",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        // ICONOS DE LISTA COMPARTIDA Y ELIMINAR LISTA
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(60.dp)
                        ) {
                            if (list.shared) {
                                Box(
                                    contentAlignment = Alignment.TopCenter,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(2.dp)
                                        .align(Alignment.TopCenter)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.SupervisorAccount,
                                        tint = Color.White,
                                        contentDescription = "Shared List Icon",
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .size(35.dp, 35.dp)
                                    )
                                }
                                Box(
                                    contentAlignment = Alignment.BottomCenter,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        tint = Color(0XFFBB3A3A),
                                        contentDescription = "Delete Product Icon",
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .size(35.dp, 35.dp)
                                            .clickable { toggleDeletePopupVisibility() }
                                    )
                                }
                            } else {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        tint = Color(0XFFBB3A3A),
                                        contentDescription = "Delete Product Icon",
                                        modifier = Modifier
                                            .size(35.dp, 35.dp)
                                            .clickable { toggleDeletePopupVisibility() }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            item {
                Button (
                    onClick = { toggleCreatePopupVisibility() },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    modifier = Modifier.size(width = 200.dp, height = 50.dp)
                ) {
                    Text(
                        text = "CREAR LISTA",
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }

    }
    if (isCreatePopupVisible) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                isCreatePopupVisible = CreateListScreen()
                if (!isCreatePopupVisible) {
                    navController.navigate(LateralScreens.ListsScreen.route)
                }
            }
        }
    }
    if (isDeletePopupVisible) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                isDeletePopupVisible = DeleteObjectScreen(false, userList!!)
                if (!isDeletePopupVisible) {
                    navController.navigate(LateralScreens.ListsScreen.route)
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