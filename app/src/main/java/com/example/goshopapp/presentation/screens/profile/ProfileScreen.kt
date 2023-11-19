package com.example.goshopapp.presentation.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.R
import com.example.goshopapp.data.FirebaseAuth

val buttonTextStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)

@Composable
fun ProfileScreen(navController: NavHostController) {
    val buttonColor = Color(android.graphics.Color.parseColor("#007562"))
    val authManager = FirebaseAuth()
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        // Contenido dentro del contenedor
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
        ) {
            // Texto "PERFIL" encima de la caja
            Text(
                text = "PERFIL",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            // Caja
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.Gray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Fila para "ic_profile" y "correo@example.com"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icono usuario
                        val image = painterResource(R.drawable.ic_profile)
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 80.dp, height = 80.dp)
                        )

                        // Columna para "Nombre" y "Correo electrónico"
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Nombre",
                                style = buttonTextStyle
                            )

                            authManager.getCurrentUserEmail()?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(fontSize = 14.sp)
                                )
                            }
                        }
                    }

                    // Botón "Editar Perfil" dentro de la caja
                    Button(
                        onClick = { /* Acción al hacer clic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "EDITAR PERFIL", style = buttonTextStyle)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones adicionales fuera de la caja
            ButtonWithIcon(
                onClick = { /* Acción al hacer clic */ },
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.icon_user_lists,
                text = "Mis Listas"
            )

            ButtonWithIcon(
                onClick = { /* Acción al hacer clic */ },
                modifier = Modifier.fillMaxWidth(),
                iconId = R.drawable.icon_costs,
                text = "Mis gastos"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Cerrar Sesión"
            Button(
                onClick = {
                    navController.popBackStack()
                    //navController.navigate(AppScreens.HomeScreen.route)

                    authManager.logout()
                    //navController.navigate(LateralScreens.LoginScreen.route)
                          },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(buttonColor, contentColor = Color.White),
                shape = RoundedCornerShape(10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "CERRAR SESIÓN",
                        style = buttonTextStyle,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(10.dp)) // Espacio entre texto e icono

                    Image(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(Color.White, contentColor = Color.Black),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(32.dp)) // Espacio entre icono y texto
            Text(
                text = text,
                style = buttonTextStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}
