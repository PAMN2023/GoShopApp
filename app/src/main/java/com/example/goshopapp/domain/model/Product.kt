package com.example.goshopapp.model

data class Product(
    val name: String = "",
    val price: String = "",
    val image: String = ""
) {
    companion object {
        fun fromMap(productMap: Map<String, Any>): Product {
            return Product(
                name = productMap["name"] as String,
                price = (productMap["price"] as String),
                image = productMap["image"] as String
            )
        }
    }
}