package utils

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.edit
import com.example.vitesse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestPermissionsOnFirstLaunch() {
    val context = LocalContext.current

    val permissions = listOf(
        Manifest.permission.CALL_PHONE,
//        Manifest.permission.SEND_SMS,
//        Manifest.permission.READ_CONTACTS
    )

    var alertDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val allGranted = permissionsMap.all { it.value }
        if (!allGranted) { alertDialog = true
//            Toast.makeText(context, "As you denied authorization to call from application you won't be able to call applicant from app. Go to settings if you want to enable this feature.", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            prefs.edit { putBoolean("isFirstLaunch", false) }
            launcher.launch(permissions.toTypedArray())
        }
    }

    if (alertDialog){
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(R.string.calls_denied)) },
            text = { Text(stringResource(R.string.call_feature_message)) },
            confirmButton = { TextButton(onClick = { alertDialog = false }) { Text(text = stringResource(R.string.ok), color = MaterialTheme.colorScheme.onPrimary) } },
            containerColor = MaterialTheme.colorScheme.primary,
        )
    }
}