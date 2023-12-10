package com.example.goshopapp.data

import android.os.StrictMode
import com.example.goshopapp.domain.model.Product
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class APIRequest(val productId: String) {
    fun execute(): Product {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder()
                .url("https://world.openfoodfacts.net/api/v2/product/$productId?fields=product_name,ingredients_text,image_url")
                .build()
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val jsonObject = JSONObject(response.body!!.string())
                val productData = jsonObject.getJSONObject("product")

                // Comprobar si el campo nombre existe
                val productName = if (productData.has("product_name")) {
                    productData["product_name"].toString().trim().replace(Regex("\\s+"), " ").replace(Regex("[^a-zA-Z0-9\\s]"), "")
                } else {
                    "product not found"
                }

                // Comprobar si el campo imagen existe
                val productImage = if (productData.has("image_url")) {
                    productData["image_url"].toString()
                } else {
                    "https://firebasestorage.googleapis.com/v0/b/goshopapp-f2548.appspot.com/o/Iconos%2Fplaceholder.png?alt=media&token=c0067f0d-2242-4bc6-a224-3dc0643933df"
                }

                // Comprobar si el campo ingredientes existe
                var ingredients = if (productData.has("ingredients_text")) {
                    productData["ingredients_text"].toString()
                } else {
                    ""
                }

                // Comprobar si los ingredientes están vacíos
                if (ingredients == "") {
                    ingredients = "Ingredientes no disponibles para este producto"
                }

                // Si los ingredientes exceden los caracteres máximos...
                if (ingredients.length >= 250) {
                    ingredients = ingredients.substring(0, 250).trim() + "..."
                }

                // Obtención del precio
                val numeroDouble = Random.nextDouble(0.0, 4.0)
                val numeroString = "%.2f".format(numeroDouble)

                return Product(
                    // Nombre
                    productName.ifEmpty { "product not found" },
                    // Descripción (Temporalmente es el nombre)
                    productName.ifEmpty { "product not found" },
                    // Ingredientes (Campo information del producto en BDD)
                    ingredients,
                    // Precio (Random)
                    numeroString.replace(",", "."),
                    // Imagen
                    productImage.ifEmpty { "https://firebasestorage.googleapis.com/v0/b/goshopapp-f2548.appspot.com/o/Iconos%2Fplaceholder.png?alt=media&token=c0067f0d-2242-4bc6-a224-3dc0643933df" }
                )
            } else {
                // Si la respuesta no es exitosa, asignar "API response fail" a todos los campos
                return Product(
                    "product not found",
                    "product not found",
                    "product not found",
                    "product not found",
                    "product not found"
                )
            }
        } catch (e: SocketTimeoutException) {
            // Retornar un producto que indica el fallo de la API
            return Product(
                "API response failed",
                "API response failed",
                "API response failed",
                "API response failed",
                "API response failed"
            )
        }
    }
}