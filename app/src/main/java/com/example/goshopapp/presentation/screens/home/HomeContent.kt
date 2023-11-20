package com.example.goshopapp.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.goshopapp.domain.model.HomePageData
import com.example.goshopapp.domain.model.Product

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(homeData: HomePageData) {
    val pagerState = rememberPagerState { 3 }
    val sliderList: List<String> = homeData.slider
    val productsList: List<Product> = homeData.products

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                text = "Bienvenidos",
                //modifier = Modifier.align(Alignment.Start)
            )
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 65.dp),
                modifier = Modifier
                    .height(100.dp)
            ) {page ->
                Card(
                    shape = RoundedCornerShape(10.dp)
                ) {
                    // Extrae las URL de las imágenes del slider
                    if (sliderList.isNotEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(sliderList[page])
                                .crossfade(true)
                                .scale(Scale.FILL)
                                .build(),
                            contentDescription = null
                        )
                    }
                }
            }
        }

        items(productsList) { product ->
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
        }
    }


    /*}

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(productsList) { product ->
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
            Text(text = product.name)

            AsyncImage(
                model = product.image,
                contentDescription = "Translated description of what the image contains",
            )
        }
    }*/
}