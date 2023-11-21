package com.example.goshopapp.data

import android.util.Log
import com.example.goshopapp.domain.interfaces.HomePageDataCallback
import com.example.goshopapp.domain.model.HomePageData
import com.example.goshopapp.domain.model.Product
import com.example.goshopapp.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseFirestoreManage {

    private val fireStore = FirebaseFirestore.getInstance()

    fun createUser(uid: String, name: String, surname: String, mail: String) {
        val userData = User(name.toString(), surname.toString(), mail.toString()).toMap()
        fireStore.collection("Usuarios").document(uid).set(userData)
            .addOnSuccessListener {
                Log.d("Completado", "Creado ${it}")
            }.addOnFailureListener{
                Log.d("Error", "Falló ${it}")
            }
    }

    fun createUserList(uid: String, listName: String) {
        fireStore.collection("Usuarios").document(uid).collection(listName)
    }

    fun addItemToUserList(uid: String, listName: String, item: String) {
        fireStore.collection("Usuarios").document(uid).collection(listName).add(item)
    }

    fun getHomePageData(callback: HomePageDataCallback) {
        val homeDocument = fireStore.collection("Destacados").document("SCcrItRzsV0ZRcl0pzDU")

        homeDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // El documento existe
                    val slider = documentSnapshot.get("slider") as? List<String> ?: emptyList()
                    val productsList =
                        documentSnapshot.get("products") as? List<Map<String, Any>> ?: emptyList()

                    // Mapear los datos de productsList a una lista de Product
                    val products = productsList.map { productMap ->
                        val name = productMap["name"] as? String ?: ""
                        val price = productMap["price"] as? String ?: ""
                        val image = productMap["image"] as? String ?: ""
                        Product(name, price, image)
                    }

                    val inspirationImage = documentSnapshot.get("inspirationImage") as? String ?: ""

                    // Crear el objeto HomePageData
                    val homeData = HomePageData(slider, products, inspirationImage)
                    callback.onHomePageDataReceived(homeData)

                    Log.d("DatosHome", homeData.toString())
                } else {
                    callback.onHomePageDataError(Exception("El documento no existe"))

                    Log.d("DatosHome", "El documento no funciona")
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
                Log.d("Error", "Error al obtener datos: $exception")
            }
    }
    
}