package com.example.goshopapp.presentation.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.goshopapp.data.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val authManager = FirebaseAuth()

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
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Email") }
        )
        TextField(
            value = passwordState.value,
            onValueChange = { newValue ->
                passwordState.value = newValue
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    authManager.register(emailState.toString(), passwordState.toString()) { success, message ->
                        if (success) {
                            println("Registro exitoso")
                        } else {
                            println("Registro fallido")
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Register")
            }
            Spacer(modifier = Modifier.width(16.dp)) // Espaciado entre botones
            Button(
                onClick = {
                    authManager.login(emailState.toString(), passwordState.toString()) { success, message ->
                        if (success) {
                            println("Inicio exitoso")
                        } else {
                            println("Inicio fallido")
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RegisterScreen()
}
