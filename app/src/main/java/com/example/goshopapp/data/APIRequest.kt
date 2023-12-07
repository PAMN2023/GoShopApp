package com.example.goshopapp.data

import com.example.goshopapp.domain.model.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class APIRequest(val productId: String) {
    fun execute(): Product {
        val request = Request.Builder()
            .url("https://world.openfoodfacts.net/api/v2/product/$productId")
            .build()
        val response = OkHttpClient().newCall(request).execute()
        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body!!.string())
            val productData = jsonObject.getJSONObject("product")
            return Product(
                productData["product_name"].toString(),
                "description",
                "information",
                "price",
                productData["image_url"].toString()
            )
        } else {
            throw Exception("Error al realizar la consulta")
        }
    }
}