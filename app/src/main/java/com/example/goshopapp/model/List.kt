package com.example.goshopapp.model

data class List(
    val name: String,
    val items: MutableList<String> = mutableListOf()
) {
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "items" to this.items
        )
    }
}
