package com.example.goshopapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class PopUpMaker() {
    @Composable
    fun createPopUp(onDismiss: () -> Unit, msg: String) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = onDismiss) {
                    Text("OK", color = Color.White)
                }
            },
            contentColor = Color.White,
            content = {
                Text(text = msg)
            },
            actionOnNewLine = true,
        )
    }
}
