package com.example.goshopapp.domain.model

data class Product(
    val name: String = "",
    val description: String = "",
    val information: String = "",
    val price: String = "",
    val image: String = ""
) {
    companion object {
        fun fromMap(productMap: Map<String, Any>): Product {
            return Product(
                name = productMap["name"] as String,
                description = productMap["description"] as String,
                information = productMap["information"] as String,
                price = productMap["price"] as String,
                image = productMap["image"] as String
            )
        }
    }
}