package com.example.goshopapp.data

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuth {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun googleRegister() {

    }

    fun googleLogin() {

    }

    fun getCurrentUserId(): String? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.uid
    }
}
