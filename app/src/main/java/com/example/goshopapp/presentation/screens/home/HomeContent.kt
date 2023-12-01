package com.example.goshopapp.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.domain.model.HomePageData
import com.example.goshopapp.domain.model.Product
import com.example.goshopapp.presentation.navigation.AppScreens
import java.net.URLEncoder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(homeData: HomePageData, navController: NavHostController) {
    val pagerState = rememberPagerState(initialPage = 1) { 3 }
    val sliderList: List<Product> = homeData.slider
    val productsList: List<Product> = homeData.products
    val inspirationImage: String = homeData.inspiration

    // LAZY COLUMN PARA HACER SCROLL DE TODO EL CONTENIDO
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // PRIMERA SECCIÓN (NOVEDADES)
        item {
            // TÍTULO
            Text(
                text = "Novedades",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                style = TextStyle(
                    color = Color(0XFF007562),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            // SUBTÍTULO
            Text(
                text = "Productos recién añadidos y tendencias",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp),
                style = TextStyle(
                    color = Color(0XFFC9511D),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            // SLIDER
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
            ) {page ->
                // COLUMNA QUE CONFORMA EL NOMBRE Y LA IMAGEN DEL SLIDER
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(16.dp)
                        .clickable {
                            val encodedImageURL = URLEncoder.encode(sliderList[page].image, "UTF-8")
                            Log.d("URL", encodedImageURL)
                            navController.navigate(AppScreens.ProductDetailsScreen.route
                                    + "/${sliderList[page].name}"
                                    + "/$encodedImageURL"
                                    + "/${sliderList[page].description}"
                                    + "/${sliderList[page].information}"
                                    + "/${sliderList[page].price}"
                            )
                        }
                ) {
                    Text(
                        text = sliderList[page].name,
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    if (sliderList.isNotEmpty()) {
                        AsyncImage(
                            model = sliderList[page].image,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // CÍRCULOS DEL SLIDER
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(sliderList.size) { it ->
                    val color =
                        if (pagerState.currentPage == it) Color(0XFF007562) else Color(0XFF45BDAA)
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .size(10.dp)
                            .background(color)
                    ) {

                    }
                }
            }
        }

        // SEGUNDA SECCIÓN (BAJADAS DE PRECIO)
        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFFF1F1F1)),
            ) {
                // TÍTULO
                Text(
                    text = "Ofertas",
                    modifier = Modifier
                       .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                    style = TextStyle(
                        color = Color(0XFF007562),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                // SUBTÍTULO
                Text(
                    text = "Bajadas de precios y oportunidades",
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 16.dp),
                    style = TextStyle(
                        color = Color(0XFFC9511D),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // ITEMS EN BAJADA DE PRECIO (SE DIVIDE ARRAY DE 2 EN 2)
        items(productsList.chunked(2)) { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFFF1F1F1)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                for (product in rowProducts) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .width(175.dp)
                            .height(275.dp)
                            .padding(16.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(16.dp)
                            .clickable {
                                val encodedImageURL = URLEncoder.encode(product.image, "UTF-8")
                                navController.navigate(AppScreens.ProductDetailsScreen.route
                                        + "/${product.name}"
                                        + "/$encodedImageURL"
                                        + "/${product.description}"
                                        + "/${product.information}"
                                        + "/${product.price}")
                            }
                    ) {
                        // IMAGEN
                        if (sliderList.isNotEmpty()) {
                            AsyncImage(
                                model = product.image,
                                modifier = Modifier
                                    .size(100.dp),
                                contentScale = ContentScale.Fit,
                                contentDescription = null
                            )
                        }
                        // NOMBRE DEL PRODUCTO
                        Text(
                            text = product.name,
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        // PRECIO DEL PRODUCTO
                        Text(
                            text = product.price.plus(" €"),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(0XFFFF0000),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        // BOTÓN DE AÑADIR PRODUCTO
                        Button(
                            onClick = {  },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(width = 100.dp, height = 30.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "Añadir"
                            )
                        }
                    }
                }
            }
        }

        // TERCERA SECCIÓN (INSPÍRATE)
        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // TÍTULO
                Text(
                    text = "Inspírate",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 15.dp, bottom = 8.dp),
                    style = TextStyle(
                        color = Color(0XFF007562),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                // SUBTÍTULO
                Text(
                    text = "Platos destacados y cocina internacional",
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 24.dp),
                    style = TextStyle(
                        color = Color(0XFFC9511D),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // IMAGEN Y DESCRIPCIÓN DE LA ÚLTIMA SECCIÓN
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = inspirationImage,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .padding(start = 30.dp, end = 30.dp)
                    ,
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )

                Text(
                    text = "Descubre lo mejor de la cocina italiana",
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 15.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}
