package com.example.goshopapp.presentation.components

interface ToastListener {
    fun showToast(message: String)
}
class PopUpMaker(private val toastListener: ToastListener) {

        fun showToastMsg(msg: String) {
            toastListener.showToast(msg)
        }

    }
