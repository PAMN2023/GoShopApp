package com.example.goshopapp.domain.model

data class User(
    val userName: String,
    val userSurname: String,
    val userMail: String,
    val fav: MutableList<String> = mutableListOf()
){
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "userName" to this.userName,
            "userSurname" to this.userSurname,
            "userMail" to this.userMail,
            "fav" to this.fav
        )
    }
}
