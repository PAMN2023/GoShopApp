package com.example.goshopapp.data

import android.os.StrictMode
import com.example.goshopapp.domain.model.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.random.Random

class APIRequest(val productId: String) {
    fun execute(): Product {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val request = Request.Builder()
            .url("https://world.openfoodfacts.net/api/v2/product/$productId?fields=product_name,ingredients_text,image_url")
            .build()
        val response = OkHttpClient().newCall(request).execute()
        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body!!.string())
            val productData = jsonObject.getJSONObject("product")
            var ingredients = productData["ingredients_text"].toString()

            // Comprobar si los ingredientes existen
            if (ingredients == "") {
                ingredients = "Ingredientes no disponibles para este producto"
            }

            // Si los ingredientes exceden los caracteres máximos...
            if (ingredients.length >= 250) {
                ingredients = ingredients.substring(0,250).trim() + "..."
            }

            // Obtención del precio
            val numeroDouble = Random.nextDouble(0.0, 4.0)
            val numeroString = "%.2f".format(numeroDouble)

            return Product(
                // Nombre
                productData["product_name"].toString().trim().replace(Regex("\\s+"), " ").replace(Regex("[^a-zA-Z0-9\\s]"), ""),
                // Descripción (Temporalmente es el nombre)
                productData["product_name"].toString().trim().replace(Regex("\\s+"), " ").replace(Regex("[^a-zA-Z0-9\\s]"), ""),
                // Ingredientes (Campo information del producto en BDD)
                ingredients,
                // Precio (Random)
                numeroString.replace(",","."),
                // Imagen
                productData["image_url"].toString()
            )
        } else {
            return Product(
                "product not found",
                "product not found",
                "product not found",
                "product not found",
                "product not found"
            )
        }
    }
}