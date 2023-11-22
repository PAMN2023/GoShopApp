package com.example.goshopapp.domain.model

data class Lists(
    val name: String,
    val items: MutableList<Product> = mutableListOf()
) {
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "items" to this.items
        )
    }
}
