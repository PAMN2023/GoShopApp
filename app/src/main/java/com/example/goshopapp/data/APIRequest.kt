package com.example.goshopapp.data

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.example.goshopapp.domain.model.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class APIRequest(val productId: String) {
    fun execute(): Product {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val request = Request.Builder()
            .url("https://world.openfoodfacts.net/api/v2/product/$productId?fields=product_name,ingredients_text,image_url")
            .build()
        val response = OkHttpClient().newCall(request).execute()
        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body!!.string())
            val productData = jsonObject.getJSONObject("product")
            return Product(
                productData["product_name"].toString().replace(".","").replace("  ", " "),
                "description",
                productData["ingredients_text"].toString(),
                "2.99",
                productData["image_url"].toString()
            )
        } else {
            throw Exception("Error al realizar la consulta")
        }
    }
}