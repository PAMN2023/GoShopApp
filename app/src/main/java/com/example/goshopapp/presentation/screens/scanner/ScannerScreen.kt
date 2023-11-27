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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun ScannerScreen() {
    val buttonColor = ComposeColor(android.graphics.Color.parseColor("#007562"))
    val context = LocalContext.current
    var scannedValue by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Inicializar el lanzador de resultados de la actividad de escaneo
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        handleActivityResult(result.resultCode, result.data) { value ->
            // Almacenar el valor escaneado y mostrar el diálogo
            scannedValue = value
            showDialog = true
        }
    }

    // Iniciar la cámara y escanear al entrar en la pantalla
    LaunchedEffect(key1 = scannedValue) {
        if (scannedValue == null && hasCameraPermission(context)) {
            openCamera(context, launcher)
        } else {
            // Lógica después de aceptar el código de barras
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        // Mostrar el diálogo si showDialog es true
        if (showDialog) {
            ShowBarcodeDialog(scannedValue ?: "") {
                // Reiniciar el valor escaneado y activar la cámara nuevamente
                scannedValue = null
                showDialog = false
            }
        }
    }
}

// Función para abrir la cámara y escanear el código de barras
private fun openCamera(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val integrator = IntentIntegrator(context as Activity)
        .setDesiredBarcodeFormats(IntentIntegrator.EAN_13)
        .setPrompt("Escanea tu producto")
        .setOrientationLocked(false)
        .setBeepEnabled(true)
        .createScanIntent()

    launcher.launch(integrator)
}

// Función para verificar si se otorgó el permiso de cámara
private fun hasCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
}

// Función para manejar el resultado de la actividad de escaneo
private fun handleActivityResult(resultCode: Int, data: Intent?, scannedValueCallback: (String) -> Unit) {
    val result = IntentIntegrator.parseActivityResult(resultCode, data)
    if (result != null) {
        if (result.contents != null) {
            val barcodeValue = result.contents
            scannedValueCallback(barcodeValue)
        } else {
            // Handle case where scanning was canceled or failed
            // TODO: Handle accordingly
        }
    }
}

// Código de solicitud para el permiso de la cámara
private const val CAMERA_PERMISSION_REQUEST_CODE = 1001

// Mostrar el diálogo con el valor del código de barras
@Composable
fun ShowBarcodeDialog(barcodeValue: String, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onClose() },
        title = { Text("Código de Barras") },
        text = { Text("Valor: $barcodeValue") },
        confirmButton = {
            Button(
                onClick = {
                    onClose()
                }
            ) {
                Text("Aceptar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ScannerScreenPreview() {
    MaterialTheme {
        Surface {
            ScannerScreen()
        }
    }
}
