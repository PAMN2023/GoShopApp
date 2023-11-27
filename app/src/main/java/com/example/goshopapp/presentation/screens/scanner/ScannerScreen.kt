package com.example.goshopapp.presentation.screens.scanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun ScannerScreen() {
    val buttonColor = Color(android.graphics.Color.parseColor("#007562"))
    val context = LocalContext.current

    // Obtener el launcher para la actividad de cámara
    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            // Manejar el resultado si es necesario
            println("ActivityResult: ${result.resultCode}")
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = {
                // Acción al hacer clic en el botón: abrir la cámara
                if (hasCameraPermission(context)) {
                    openCamera(context, cameraLauncher)
                } else {
                    requestCameraPermission(context)
                }
            },
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(buttonColor),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Leer código de barras")
        }
    }
}

private fun openCamera(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
    launcher.launch(cameraIntent)
}

private fun hasCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
}

private fun requestCameraPermission(context: Context) {
    // Aquí deberías solicitar el permiso en tiempo de ejecución
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(android.Manifest.permission.CAMERA),
        CAMERA_PERMISSION_REQUEST_CODE
    )
}

private const val CAMERA_PERMISSION_REQUEST_CODE = 1001

@Preview(showBackground = true)
@Composable
fun ScannerScreenPreview() {
    MaterialTheme {
        Surface {
            ScannerScreen()
        }
    }
}
