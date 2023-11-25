package com.example.goshopapp.presentation.screens.listdetails

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.goshopapp.presentation.viewmodel.ListDetailsViewModel

@Composable
fun ListDetailsScreen(listDetailsViewModel: ListDetailsViewModel) {

    val items = listDetailsViewModel.items

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
                        Text(
                        text = listDetailsViewModel.listName,
                        color = Color(android.graphics.Color.parseColor("#007562")),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (listDetailsViewModel.isShared) {
                            Icon(
                                imageVector = Icons.Filled.SupervisedUserCircle,
                                tint = Color(0XFF007562),
                                contentDescription = "Shared List Icon",
                                modifier = Modifier
                                    .padding(end = 15.dp)
                                    .size(40.dp, 40.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            items(items) { product ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(16.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = product.image,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(70.dp),
                        contentScale = ContentScale.Fit,
                        contentDescription = null
                    )

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
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Cantidad: 1",
                            color = Color(0XFF007562),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Precio aproximado: " + product.price.toDouble().toInt() + "€",
                            color = Color(0XFF00B697),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(60.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                tint = Color.Red,
                                contentDescription = "Delete Product Icon",
                                modifier = Modifier
                                    .background(Color.White)
                                    .size(35.dp, 35.dp)
                                    .clickable { }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(6.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* accion al pulsar */ },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(width = 250.dp, height = 50.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "AÑADIR COMO GASTO"
                        )
                    }
                }
            }
        }
    }
}