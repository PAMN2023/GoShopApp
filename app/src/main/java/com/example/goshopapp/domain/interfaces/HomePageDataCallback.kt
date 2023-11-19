package com.example.goshopapp.domain.interfaces

import com.example.goshopapp.domain.model.HomePageData

interface HomePageDataCallback {
    fun onHomePageDataReceived(homeData: HomePageData)
    fun onHomePageDataError(error: Exception)
}