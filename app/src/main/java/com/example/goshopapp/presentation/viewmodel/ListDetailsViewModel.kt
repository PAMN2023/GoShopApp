package com.example.goshopapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.goshopapp.domain.model.Product

class ListDetailsViewModel : ViewModel() {
    val items = mutableListOf<Product>()
    var listName = ""
    var isShared = false
}