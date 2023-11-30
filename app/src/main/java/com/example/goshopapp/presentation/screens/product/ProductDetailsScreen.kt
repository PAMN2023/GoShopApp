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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.goshopapp.domain.model.Product
import com.example.goshopapp.presentation.screens.actionpopups.AddItemToListScreen
import com.example.goshopapp.presentation.screens.actionpopups.CreateListScreen

@Composable
fun ProductDetailsScreen(
    productName: String?,
    productImage: String?,
    productDescription: String?,
    productInformation: String?,
    productPrice: String?
    ) {
    var isPopupVisible by remember { mutableStateOf(false) }
    fun togglePopupVisibility() {
        isPopupVisible = !isPopupVisible
    }
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
            IconButton(
                onClick = { /* Acción al hacer clic */ },
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(75.dp)
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favourites Button",
                    tint = Color.White
                )
            }
        }
    }
    if (isPopupVisible) {
        AddItemToListScreen(Product(productName!!,productDescription!!,productInformation!!,productPrice!!,productImage!!))
    }
}
