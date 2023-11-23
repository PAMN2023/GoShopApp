package com.example.goshopapp.domain.model

data class Lists(
    val name: String,
    val shared: Boolean,
    val items: MutableList<Product> = mutableListOf()
) {
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "shared" to this.shared,
            "items" to this.items
        )
    }
}
