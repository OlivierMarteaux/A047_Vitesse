package extensions

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

/**
 * Initiates a phone call to the given [phoneNumber].
 * Checks if there is an activity available to handle the call intent before starting it.
 *
 * @receiver Context used to start the call activity.
 * @param phoneNumber The phone number to call, formatted as a string.
 */
fun Context.callPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = "tel:$phoneNumber".toUri()
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

/**
 * Opens the default SMS app with the specified [phoneNumber] and optional [message].
 * Checks if there is an activity available to handle the intent before starting it.
 *
 * @receiver Context used to start the SMS activity.
 * @param phoneNumber The recipient phone number.
 * @param message The SMS body text. Defaults to empty string.
 */
fun Context.sendSms(phoneNumber: String, message: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "smsto:$phoneNumber".toUri() // Use smsto: instead of tel:
        putExtra("sms_body", message)
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

/**
 * Opens the default email app with the specified recipient, subject, and body.
 * Checks if there is an activity available to handle the intent before starting it.
 *
 * @receiver Context used to start the email activity.
 * @param recipient The email recipient address.
 * @param subject The email subject. Defaults to empty string.
 * @param body The email body text. Defaults to empty string.
 */
fun Context.sendEmail(recipient: String, subject: String = "", body: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:$recipient".toUri()
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}