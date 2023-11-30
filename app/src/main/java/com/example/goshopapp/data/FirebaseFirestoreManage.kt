package com.example.goshopapp.data

import android.util.Log
import com.example.goshopapp.domain.interfaces.HomePageDataCallback
import com.example.goshopapp.domain.interfaces.UserDataCallback
import com.example.goshopapp.domain.interfaces.UserListsCallback
import com.example.goshopapp.domain.model.HomePageData
import com.example.goshopapp.domain.model.Lists
import com.example.goshopapp.domain.model.Product
import com.example.goshopapp.domain.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.round

class FirebaseFirestoreManage {

    private val fireStore = FirebaseFirestore.getInstance()

    /**
     * Creates a new user account in Firestore and adds a default "Favoritos" list to their `Listas` sub-collection.
     *
     * @param uid 'String' with the unique identifier of the user
     * @param name 'String' with the user's name
     * @param surname 'String' with the user's surname
     * @param mail 'String' with the user's email address
     * @return A `Boolean` value indicating whether the user was successfully created and the default list was added
     */
    fun createUser(uid: String, name: String, surname: String, mail: String): Boolean {
        var response = false
        val userData = User(name.toString(), surname.toString(), mail.toString()).toMap()
        fireStore.collection("Usuarios").document(uid).set(userData)
            .addOnSuccessListener {
                Log.d("Completado", "Creado ${it}")
                response = true
            }.addOnFailureListener{
                Log.d("Error", "Falló ${it}")
            }
        val items: MutableList<Product> = mutableListOf()
        val favData = Lists("Favoritos", false, "0", "https://cdn.icon-icons.com/icons2/290/PNG/512/favourites_30824.png", items).toMap()
        fireStore.collection("Usuarios").document(uid).collection("Listas").document("Favoritos").set(favData)
            .addOnSuccessListener{
                response = true
            }.addOnFailureListener {
                response = false
                Log.d("Error", "Falló La creación de la colección de Listas del usuario")
            }
        return response
    }

    /**
     * Creates a new list within the user's `Listas` sub-collection if not exists 
     *  a list with the same name.
     *
     * @param uid 'String' with the unique identifier of the user
     * @param listName 'String' with the name of the list to create
     * @return A `Boolean` value indicating whether the list was successfully created
     */
    fun createUserList(uid: String, listName: String, img: String): Boolean {
        var response = false
        val items: MutableList<Product> = mutableListOf()
        val listData = Lists(listName, false, "0", img, items).toMap()
        fireStore.collection("Usuarios").document(uid).collection("Listas").document(listName).set(listData)
            .addOnSuccessListener{
                response = true
            }
        return response
    }

    /**
     * Deletes a specific list from the user's `Listas` sub-collection.
     *
     * @param uid 'String' with the unique identifier of the user
     * @param listName 'String' with the name of the list to delete
     * @return A `Boolean` value indicating whether the list was successfully deleted
     */
    fun deleteUserList(uid: String, listName: String): Boolean {
        var response = false
        fireStore.collection("Usuarios").document(uid).collection("Listas").document(listName).delete()
            .addOnSuccessListener{
                response = true
            }
        return response
    }

    /**
     * Adds new items to a specific list within the user's `Listas` sub-collection.
     *
     * @param uid 'String' with the unique identifier of the user
     * @param listName 'String' with the name of the list to add items to
     * @param item A `Product` representing the item to add
     * @return A `Boolean` value indicating whether the items were successfully added
     */
    fun addItemToUserList(uid: String, listName: String, item: Product): Boolean {
        var response = false
        val userList = getUserListById(uid, listName)
        userList?.items?.add(item)
        val listData = userList?.let { Lists(listName, userList.shared, userList.aproxPrice, userList.image, it.items).toMap() }
        if (listData != null) {
            fireStore.collection("Usuarios").document(uid).collection("Listas").document(listName).set(listData)
                .addOnSuccessListener{
                    response = true
                }
        }
        return response
    }

    /**
     * Removes an item from a specific list within the user's `Listas` sub-collection.
     *
     * @param uid 'String' with the unique identifier of the user
     * @param listName 'String' with the name of the list to remove the item from
     * @param itemName 'String' with the name of the item to delete
     * @return 'Boolean' that indicates the result of the firestore replay
     */
    fun deleteItemOfUserList(uid: String, listName: String, itemName: String): Boolean {
        var response = false
        val userList = getUserListById(uid, listName)
        userList?.items?.forEach{
            if (it.name == itemName) {
                userList.items.remove(it)
                Log.d("Deletion", "Item with name '$itemName' removed")
            }
        }
        val listData = userList?.let { Lists(listName, it.shared, userList.aproxPrice, userList.image, userList.items).toMap() }
        if (listData != null) {
            fireStore.collection("Usuarios").document(uid).collection("Listas").document(listName).set(listData)
                .addOnSuccessListener{
                    response = true
                }
        }
        return response
    }

    /**
     * Obtains a list by the id of the firestore document id
     *
     * @param uid 'String' with the unique identifier of the user
     * @param listId 'String' with the id of the list to return
     * @return A `Lists` object with the data of the document on firestore
     */
    private fun getUserListById(uid: String, listId: String): Lists? {
        var userList: Lists? = null
        fireStore.collection("Usuarios").document(uid).collection("Listas").document(listId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    userList = documentSnapshot.toObject(Lists::class.java)!!
                }
            }
        return userList
    }

    /**
     * Retrieves a reference to the `Listas` sub-collection within the user document in Firestore.
     *
     * @param uid a 'String' representing the unique identifier of the user
     * @return A `CollectionReference` object representing the `Listas` sub-collection
     */
    fun getUserLists(uid: String): CollectionReference {
        return fireStore.collection("Usuarios").document(uid).collection("Listas")
    }

    /**
     * Retrives an iterable of the user lists
     *
     * @param uid unique id of the user
     * @param callback The callback to obtain the iterable user lists
     */
    fun getIterableUserLists(uid: String, callback: UserListsCallback) {
        val userLists: MutableList<Lists> = mutableListOf()

        getUserLists(uid).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Error", "Error getting user lists: ${error.message}")
                return@addSnapshotListener
            }

            snapshot?.documents?.forEach { document ->
                val documentData = document.data
                if (documentData != null) {
                    val itemsList = documentData["items"] as? List<Map<String, Any>>
                    val productList = itemsList?.map { Product.fromMap(it) } ?: emptyList()

                    // Calcular el precio total de la lista de productos
                    var totalPrice = productList.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
                    val defaultTotalPrice = if (totalPrice == 0.0) 0 else round(totalPrice).toInt()

                    val listData = Lists(
                        documentData["name"].toString(),
                        documentData["shared"] as Boolean,
                        defaultTotalPrice.toString(),
                        documentData["image"].toString(),
                        productList.toMutableList()
                    )

                    userLists.add(listData)
                    callback.onUserListsReceived(userLists)
                }
            }
        }
    }

    fun getHomePageData(callback: HomePageDataCallback) {
        val homeDocument = fireStore.collection("Destacados").document("SCcrItRzsV0ZRcl0pzDU")

        homeDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // El documento existe
                    val slider =
                        documentSnapshot.get("slider") as? List<Map<String, Any>> ?: emptyList()

                    val sliderProducts = slider.map { sliderMap ->
                        val name = sliderMap["name"] as? String ?: ""
                        val description = sliderMap["description"] as? String ?: ""
                        val information = sliderMap["information"] as? String ?: ""
                        val price = sliderMap["price"] as? String ?: ""
                        val image = sliderMap["image"] as? String ?: ""
                        Product(name, description, information, price, image)
                    }

                    val productsList =
                        documentSnapshot.get("products") as? List<Map<String, Any>> ?: emptyList()

                    // Mapear los datos de productsList a una lista de Product
                    val products = productsList.map { productMap ->
                        val name = productMap["name"] as? String ?: ""
                        val description = productMap["description"] as? String ?: ""
                        val information = productMap["information"] as? String ?: ""
                        val price = productMap["price"] as? String ?: ""
                        val image = productMap["image"] as? String ?: ""
                        Product(name, description, information, price, image)
                    }

                    val inspirationImage = documentSnapshot.get("inspirationImage") as? String ?: ""

                    // Crear el objeto HomePageData
                    val homeData = HomePageData(sliderProducts, products, inspirationImage)
                    callback.onHomePageDataReceived(homeData)
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

    fun getUserData(uid: String, callback: UserDataCallback) {
        val userDocument = fireStore.collection("Usuarios").document(uid)
        val resultMap = mutableMapOf<String, Any>()

        userDocument.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userName = documentSnapshot.get("userName") as? String ?: ""
                    val userSurname = documentSnapshot.get("userSurname") as? String ?: ""

                    resultMap["userName"] = userName
                    resultMap["userSurname"] = userSurname

                    callback.onUserDataReceived(resultMap)
                } else {
                    Log.d("DatosUser", "El documento no funciona")
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
                Log.d("Error", "Error al obtener datos: $exception")
            }
    }
}

//    fun getIterableUserLists(uid: String, callback: UserListsCallback) {
//        val userLists: MutableList<Lists> = mutableListOf()
//
//        getUserLists(uid).addSnapshotListener { snapshot, error ->
//            if (error != null) {
//                Log.e("Error", "Error getting user lists: ${error.message}")
//                return@addSnapshotListener
//            }
//            snapshot?.documents?.forEach { document ->
//                val documentData = document.data
//                if (documentData != null) {
//                    val listData = Lists(documentData["name"].toString(), documentData["shared"] as Boolean, documentData["aproxPrice"].toString(), documentData["image"].toString(), documentData["items"] as MutableList<Product>)
//                    userLists.add(listData)
//                    callback.onUserListsReceived(userLists)
//                }
//            }
//        }
//    }