package utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Opens the system settings screen for the current application,
 * allowing the user to modify app-specific settings such as permissions.
 *
 * @param context The context used to start the settings activity.
 *
 * Usage example:
 * ```
 * openAppSettings(context)
 * ```
 */
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}