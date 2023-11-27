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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun ScannerScreen() {
    val buttonColor = Color(android.graphics.Color.parseColor("#007562"))
    val context = LocalContext.current

    // State for showing the scanned value in the Snackbar
    var scannedValue by remember { mutableStateOf<String?>(null) }

    // Initialize the activity result launcher
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        handleActivityResult(result.resultCode, result.data) { value ->
            scannedValue = value
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = {
                if (hasCameraPermission(context)) {
                    // Start the scanning activity
                    openCamera(context, launcher)
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

        // Show Snackbar when there is a scanned value
        scannedValue?.let { value ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { scannedValue = null }) {
                        Text("OK")
                    }
                }
            ) {
                Text("Código de barras leído: $value")
            }
        }
    }
}

// Updated openCamera function to use the launcher
private fun openCamera(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val integrator = IntentIntegrator(context as Activity)
        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        .setPrompt("Escanea un código de barras")
        .setOrientationLocked(false)
        .setBeepEnabled(true)
        .createScanIntent()

    launcher.launch(integrator)
}

// Handle the result of the scanning activity
private fun handleActivityResult(resultCode: Int, data: Intent?, scannedValueCallback: (String) -> Unit) {
    val result = IntentIntegrator.parseActivityResult(resultCode, data)
    if (result != null) {
        if (result.contents != null) {
            // Handle the scanned barcode value here
            val barcodeValue = result.contents
            // Show the scanned value in the Snackbar
            scannedValueCallback(barcodeValue)
        } else {
            // Handle case where scanning was canceled or failed
            // TODO: Handle accordingly
        }
    }
}

// Function to verify if the camera permission is granted
private fun hasCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
}

// Function to request camera permission
private fun requestCameraPermission(context: Context) {
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(android.Manifest.permission.CAMERA),
        CAMERA_PERMISSION_REQUEST_CODE
    )
}

// Código de solicitud para el permiso de la cámara
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
