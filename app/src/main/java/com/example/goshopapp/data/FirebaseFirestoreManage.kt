package com.example.goshopapp.data

import android.util.Log
import com.example.goshopapp.model.User
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
    
}