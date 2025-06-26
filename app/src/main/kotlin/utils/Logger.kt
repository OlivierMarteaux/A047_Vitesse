package utils

import android.util.Log

/**
 * Simple logging interface for debug messages.
 */
interface Logger {
    /**
     * Logs a debug message.
     *
     * @param message The message to log.
     */
    fun d(message: String)
}

/**
 * [Logger] implementation that logs debug messages using Android's Log.d with a predefined tag "OM_TAG".
 */
object AndroidLogger : Logger {
    override fun d(message: String) {
        Log.d("OM_TAG", message)
    }
}

/**
 * [Logger] implementation that performs no operation.
 *
 * Useful as a default or stub logger to disable logging.
 */
object NoOpLogger : Logger {
    override fun d(message: String) = Unit
}