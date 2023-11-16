package com.example.goshopapp.presentation.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.goshopapp.data.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }
    val currentUID = remember { mutableStateOf("null") }
    val successString = remember { mutableStateOf("false") }
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(android.graphics.Color.parseColor("#007562")),
        contentColor = Color(android.graphics.Color.parseColor("#007562"))
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = emailState.value,
            onValueChange = { newValue ->
                emailState.value = newValue
            },
            modifier = Modifier.width(350.dp),
            label = { Text(text = "Email") },
            shape = MaterialTheme.shapes.extraLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = passwordState.value,
            onValueChange = { newValue ->
                passwordState.value = newValue
            },
            modifier = Modifier.width(350.dp),
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.extraLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.width(350.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if(authManage(emailState.value, passwordState.value, register = true)) {
                        success = true
                        successString.value = "true"
                    }
                },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium,
                colors = customButtonColors
            ) {
                Text(text = "Register", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (authManage(emailState.value, passwordState.value)) {
                        success = true
                        successString.value = "true"
                        currentUID.value = currentUidUpdater()
                    }
                },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium,
                colors = customButtonColors
            ) {
                Text(text = "Iniciar Sesion", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}

private fun authManage(email: String, pass: String, register: Boolean = false): Boolean {
    val authManager = FirebaseAuth()
    var result: Boolean = false
    if (register) {
        authManager.register(email, pass) { success, message ->
            if (success) {
                result = true
            }
        }
    } else {
        authManager.login(email, pass) { success, message ->
            if (success) {
                result = true
            }
        }
    }
    return result
}

private fun currentUidUpdater(): String {
    if(FirebaseAuth().getCurrentUserId() != null) {
        return FirebaseAuth().getCurrentUserId()!!
    } else {
        return "null"
    }
}
