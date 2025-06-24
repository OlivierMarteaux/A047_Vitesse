package utils

import android.util.Log

interface Logger {
    fun d(message: String)
}

object AndroidLogger : Logger {
    override fun d(message: String) {
        Log.d("OM_TAG", message)
    }
}

object NoOpLogger : Logger {
    override fun d(message: String) = Unit
}