package extensions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLocalDateString() : String {

    val usFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.US)
    val frFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)
    val formatter = when (Locale.getDefault().language) {
        Locale.FRENCH.language -> frFormatter
        Locale.ENGLISH.language -> usFormatter
        else -> usFormatter // Default fallback
    }
    return this.format(formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
fun LocalDate.upTo(): SelectableDates {
    return object : SelectableDates {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            return !selectedDate.isAfter(this@upTo)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLong() = this
    .atStartOfDay((ZoneOffset.UTC)) // Convert to ZonedDateTime
    .toInstant()                          // Convert to Instant
    .toEpochMilli()                       // Convert to epoch millis