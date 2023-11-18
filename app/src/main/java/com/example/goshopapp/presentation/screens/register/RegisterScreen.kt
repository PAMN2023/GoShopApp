package com.example.goshopapp.presentation.screens.register

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.goshopapp.R
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.navigation.LateralScreens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    val token = "929980378635-n26stsh0hjhnbdc425173p0t8je0qt7t.apps.googleusercontent.com"
    val context = LocalContext.current
    val nameState = remember { mutableStateOf("") }
    val surnameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val repitPasswordState = remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }
    val textFieldsColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        containerColor = Color(android.graphics.Color.parseColor("#D3D3D3")))
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color(android.graphics.Color.parseColor("#007562")),
        contentColor = Color(android.graphics.Color.parseColor("#007562"))
    )
    val googleLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            authManagerGoogle(credential){
                navController.navigate(AppScreens.ProfileScreen.route)
            }
        } catch (e: Exception) {
            Log.d("Error lanzadno google", "El servicio para iniciar con Google no se lanzó")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "NOMBRE",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 35.dp))

        TextField(
            value = nameState.value,
            onValueChange = { newValue ->
                nameState.value = newValue
            },
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            label = { Text(text = "Introduce tu nombre", modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 1.dp)) },
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "APELLIDOS",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 35.dp))

        TextField(
            value = surnameState.value,
            onValueChange = { newValue ->
                surnameState.value = newValue
            },
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            label = { Text(text = "Introduce tus apellidos", modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 1.dp)) },
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "EMAIL",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 35.dp))

        TextField(
            value = emailState.value,
            onValueChange = { newValue ->
                emailState.value = newValue
            },
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            label = { Text(text = "Introduce tu email", modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 1.dp)) },
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "CONTRASEÑA",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 35.dp))

        TextField(
            value = passwordState.value,
            onValueChange = { newValue ->
                passwordState.value = newValue
            },
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            label = { Text(text = "Introduce tu contraseña", modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 1.dp)) },
            visualTransformation = PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "REPITE TU CONTRASEÑA",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 35.dp))

        TextField(
            value = repitPasswordState.value,
            onValueChange = { newValue ->
                repitPasswordState.value = newValue
            },
            modifier = Modifier
                .width(350.dp)
                .height(50.dp),
            label = {Text(text = "Introduce tu contraseña", modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 1.dp))},
            visualTransformation = PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.large,
            colors = textFieldsColors
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                if (passwordState.value == repitPasswordState.value) {
                    authManage(
                        emailState.value,
                        passwordState.value,
                        nameState.value,
                        surnameState.value,
                        navController
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = customButtonColors
        ) {
            Text(text = "REGÍSTRATE", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(9.dp))

        Button(
            onClick = {
                val options = GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN
                ).requestIdToken(token).requestEmail().build()
                val googleClient = GoogleSignIn.getClient(context, options)
                googleLauncher.launch(googleClient.signInIntent)
            },
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(2.dp, Color(android.graphics.Color.parseColor("#007562"))),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "REGISTRO CON GOOGLE",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(android.graphics.Color.parseColor("#007562"))
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "¿Ya tienes cuenta?",
            color = Color(android.graphics.Color.parseColor("#007562")),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate(LateralScreens.LoginScreen.route)
            },
            shape = MaterialTheme.shapes.medium,
            colors = customButtonColors
        ) {
            Text(text = "INICIA SESIÓN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

private fun authManage(email: String, pass: String, name: String, surname: String, navController: NavHostController): Boolean {
    val authManager = FirebaseAuth()
    var result = false
    authManager.register(email, pass, name, surname) { success, _ ->
        if (success) {
            result = true
            navController.navigate(LateralScreens.LoginScreen.route)
        }
    }
    return result
}

private fun authManagerGoogle(credential: AuthCredential, nav:() -> Unit) {
    val authManager = FirebaseAuth()
    authManager.googleRegister(credential)
}
