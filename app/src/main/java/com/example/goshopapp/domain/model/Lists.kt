package com.example.goshopapp.domain.model

data class Lists(
    val name: String,
    val shared: Boolean,
    val aproxPrice: String,
    val image: String,
    val items: MutableList<Product> = mutableListOf()
) {
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "shared" to this.shared,
            "aproxPrice" to this.aproxPrice,
            "image" to this.image,
            "items" to this.items
        )
    }
}
