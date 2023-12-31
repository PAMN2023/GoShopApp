package com.example.goshopapp.presentation.screens.favourites

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.domain.model.Product
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.screens.actionpopups.DeleteObjectScreen
import java.net.URLEncoder
import kotlin.math.round

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FavouritesScreen(navController: NavHostController) {
    val storeManager = FirebaseFirestoreManage()
    val authManager = FirebaseAuth()
    var userLists by remember { mutableStateOf<MutableList<Lists>?>(null) }
    var favIndex by remember { mutableStateOf<Int>(0) }
    DisposableEffect(Unit) {
        authManager.getCurrentUserId()?.let {
            storeManager.getIterableUserLists(it, object : UserListsCallback {
                override fun onUserListsReceived(data: MutableList<Lists>) {
                    favIndex = data.indexOfFirst { it.name == "Favoritos" }
                    userLists = data
                }
                override fun onUserDataError(error: Exception) {
                    Log.d("Error", "Error al obtener datos: $error")
                }
            })
        }
        onDispose { }
    }

    val firstList: Lists? = userLists?.get(favIndex)

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
            // TÍTULO E ICONO DE LISTA COMPARTIDA
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (firstList != null) {
                            Text(
                                text = firstList.name,
                                color = Color(android.graphics.Color.parseColor("#007562")),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            // FILA CREADA POR CADA ITEM
            if (firstList != null) {
                items(firstList.items) { product ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(16.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(8.dp)
                            .clickable {
                                val encodedImageURL = URLEncoder.encode(product.image, "UTF-8")
                                navController.navigate(
                                    AppScreens.ProductDetailsScreen.route
                                        + "/${product.name}"
                                        + "/$encodedImageURL"
                                        + "/${product.description}"
                                        + "/${product.information}"
                                        + "/${product.price}")
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // IMAGEN DEL PRODUCTO
                        AsyncImage(
                            model = product.image,
                            modifier = Modifier
                                .padding(5.dp)
                                .size(70.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                        // COLUMNA CON EL NOMBRE, LA CANTIDAD Y EL PRECIO DEL PRODUCTO
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(180.dp)
                                .padding(start = 16.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = product.name,
                                color = Color(0XFF007562),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            /*Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Cantidad: 1",
                                color = Color(0XFF007562),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )*/
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Precio aproximado: " + round(product.price.toDouble()).toInt() + "€",
                                color = Color(0XFF00B697),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }
        }
    }
}