package com.example.goshopapp.domain.interfaces

import com.example.goshopapp.domain.model.Lists

interface UserIndividualListCallback {
    fun onUserListLoaded(userList: Lists?)
}