package com.example.goshopapp.domain.model

import kotlin.collections.List

data class HomePageData(
    val slider: List<Product>,
    val products: List<Product>,
    val inspiration: String
)
