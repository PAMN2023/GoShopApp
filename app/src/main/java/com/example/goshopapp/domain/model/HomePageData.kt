package com.example.goshopapp.model

import kotlin.collections.List

data class HomePageData(
    val slider: List<String>,
    val products: List<Product>
)
    /*companion object {
        fun fromFirestoreData(firestoreData: Map<String, Any>): HomePageData {
            return HomePageData(
                slider = (firestoreData["slider"] as? List<String>) ?: emptyList() as List<String>,
                products = (firestoreData["products"]?.map { productMap -> Product.fromMap(productMap) } ?: emptyList()) as List<Product>
            )
        }
    }*/
