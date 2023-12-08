package com.example.goshopapp.presentation.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.interfaces.FavItemCallback
import com.example.goshopapp.domain.model.Product
import com.example.goshopapp.presentation.screens.actionpopups.AddItemToListScreen
import java.net.URLEncoder

@Composable
fun ProductDetailsScreen(
    productName: String?,
    productImage: String?,
    productDescription: String?,
    productInformation: String?,
    productPrice: String?
) {
    val authManager = FirebaseAuth()
    val storeManager = FirebaseFirestoreManage()
    var isItemInList by remember { mutableStateOf(false) }
    var isFavourite by remember { mutableStateOf(false) }
    var isPopupVisible by remember { mutableStateOf(false) }

    fun togglePopupVisibility() {
        isPopupVisible = !isPopupVisible
    }

    DisposableEffect(Unit) {
        authManager.getCurrentUserId()?.let {
            if (productName != null) {
                storeManager.isItemInFavourites(it, "Favoritos", productName, object : FavItemCallback {
                    override fun onItemFavReceived(fav: Boolean) {
                        isItemInList = fav
                    }
                })
            }
        }
        onDispose { }
    }

    // Controlamos si el producto existe en la API o no
    if (productName == "product not found") {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Text(text = "El producto no existe")
        }
    } else {
        // COLUMNA QUE CONTENDRA TODA LA DESCRIPCIÓN DE PRODUCTO
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ){
            if (productName != null) {
                // NOMBRE DEL PRODUCTO
                Text(
                    text = productName,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color(0XFF007562),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            // IMAGEN DEL PRODUCTO
            if (productImage != null) {
                AsyncImage(
                    model = productImage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Inside,
                    contentDescription = null
                )
            }
            // DESCRIPCIÓN DEL PRODUCTO
            if (productDescription != null) {
                val descriptionText = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Descripción del producto: ")
                    }
                    append(productDescription)
                }
                Text(
                    text = descriptionText,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
            // INFORMACIÓN NUTRICIONAL DEL PRODUCTO
            if (productInformation != null) {
                val informationText = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Información nutricional: ")
                    }
                    append(productInformation)
                }
                Text(
                    text = informationText,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
            // PRECIO DEL PRODUCTO
            if (productInformation != null) {
                val priceText = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Precio aproximado: ")
                    }
                    append(productPrice)
                    append(" €")
                }
                Text(
                    text = priceText,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
            // BOTONES DE AÑADIR A LISTA Y A FAVORITOS
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button (
                    onClick = { togglePopupVisibility() },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    modifier = Modifier.size(width = 200.dp, height = 50.dp)
                ) {
                    Text(
                        text = "Añadir a Lista",
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }

                isFavourite = isItemInList

                IconButton(
                    onClick = {
                        if (isFavourite) {
                            if (
                                productName != null
                            ) {
                                deleteItemFromFavList(authManager, "Favoritos", productName)
                                isFavourite = false
                            }
                        } else {
                            if (
                                productName != null &&
                                productDescription != null &&
                                productInformation != null &&
                                productPrice != null &&
                                productImage != null
                            ) {
                                val encodedInformation =
                                    URLEncoder.encode(productInformation, "UTF-8").replace("+", " ")
                                val item: Product = Product(productName, productDescription,encodedInformation,productPrice,productImage)
                                addItemToFavList(authManager, "Favoritos", item)
                                isFavourite = true
                            }
                        }

                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .height(50.dp)
                        .width(75.dp)
                        .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                ) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favourites Button",
                        tint = Color.White
                    )
                }
            }
        }
        if (isPopupVisible) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val encodedInformation =
                        URLEncoder.encode(productInformation, "UTF-8").replace("+", " ")
                    isPopupVisible = AddItemToListScreen(Product(productName!!, productDescription!!, encodedInformation, productPrice!!, productImage!!))
                }
            }
        }
    }
}

fun addItemToFavList(
    authManager: FirebaseAuth,
    listName: String,
    item: Product
) {
    val storeManager = FirebaseFirestoreManage()

    authManager.getCurrentUserId()?.let { storeManager.addItemToUserList(it,listName,item) }
}

fun deleteItemFromFavList(
    authManager: FirebaseAuth,
    listName: String,
    itemName: String
) {
    val storeManager = FirebaseFirestoreManage()

    authManager.getCurrentUserId()?.let { storeManager.deleteItemOfUserList(it,listName,itemName) }
}
