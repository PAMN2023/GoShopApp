package com.example.goshopapp.domain.model

import kotlin.collections.List

data class HomePageData(
    val slider: List<String>,
    val products: List<Product>
)
