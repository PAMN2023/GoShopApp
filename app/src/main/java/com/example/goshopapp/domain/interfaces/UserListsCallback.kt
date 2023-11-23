package com.example.goshopapp.domain.interfaces

import com.example.goshopapp.domain.model.Lists

interface UserListsCallback {
    fun onUserListsReceived(userLists: MutableList<Lists>)
    fun onUserDataError(error: Exception)
}