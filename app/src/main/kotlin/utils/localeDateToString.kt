package utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun localeDateToString(date: LocalDate): String {

    val usFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.US)
    val frFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)
    val formatter = when (Locale.getDefault().language) {
        Locale.FRENCH.language -> frFormatter
        Locale.ENGLISH.language -> usFormatter
        else -> usFormatter // Default fallback
    }
    return date.format(formatter)
}