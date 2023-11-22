package com.example.goshopapp.data

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuth {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val fireStoreManage = FirebaseFirestoreManage()

    private fun createUserList(uid: String?, userName: String, userSurname: String, mail: String) {
        if (uid != null) {
            fireStoreManage.createUser(uid, userName, userSurname, mail)
        }
    }

    fun register(email: String, password: String, userName: String, userSurname: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    createUserList(task.result.user?.uid, userName, userSurname, email)
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

    fun resetPassword(emal: String) {
        firebaseAuth.sendPasswordResetEmail(emal)
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun googleRegister(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Completado", "Registrado con Google correctamente")
            }
        }.addOnFailureListener{
            Log.d("Error", "Registro con Google fallido")
        }
    }

    fun googleLogin(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Completado", "Inicio con Google correctamente")
            }
        }.addOnFailureListener{
            Log.d("Error", "Inicio con Google fallido")
        }
    }

    fun getCurrentUserId(): String? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.uid
    }

    fun getCurrentUserEmail(): String? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.email
    }
}
