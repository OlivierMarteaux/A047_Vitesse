package utils

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

/**
 * Copies an image from the given [uri] to the app's internal storage directory.
 *
 * This function opens an input stream from the provided [uri] and writes its content
 * into a new file located in the app's internal storage. The new file is named with
 * a timestamp to ensure uniqueness and saved with a ".jpg" extension.
 *
 * @param context The context used to access the content resolver and file system.
 * @param uri The source URI of the image to copy.
 * @return A new [Uri] pointing to the copied image file in internal storage, or `null` if the operation fails.
 *
 * Usage example:
 * ```
 * val persistentUri = copyImageToInternalStorage(context, selectedImageUri)
 * ```
 */
fun copyImageToInternalStorage(context: Context, uri: Uri): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        file.toUri() // Return a persistent URI
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}