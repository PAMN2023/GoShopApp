package com.example.goshopapp.domain.interfaces

interface UserDataCallback {
    fun onUserDataReceived(userMap: Map<String,Any>)
    fun onUserDataError(error: Exception)
}