package extensions

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun Context.callPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = "tel:$phoneNumber".toUri()
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.sendSms(phoneNumber: String, message: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "smsto:$phoneNumber".toUri() // Use smsto: instead of tel:
        putExtra("sms_body", message)
    }

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

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