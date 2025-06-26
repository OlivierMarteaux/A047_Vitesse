package extensions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Converts the [LocalDate] to a localized string representation based on the device locale.
 * Uses "MM/dd/yyyy" for English locales and "dd/MM/yyyy" for French or default.
 *
 * Requires API level 26 or higher.
 *
 * @receiver The [LocalDate] to format.
 * @return The formatted date string.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLocalDateString() : String {

    val ukFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.UK)
    val frFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)
    val formatter = when (Locale.getDefault().language) {
        Locale.FRENCH.language -> frFormatter
        Locale.ENGLISH.language -> ukFormatter
        else -> frFormatter // Default fallback
    }
    return this.format(formatter)
}

/**
 * Creates a [SelectableDates] implementation for Compose date pickers,
 * allowing selection of dates up to and including this [LocalDate].
 *
 * Requires API level 26 or higher.
 *
 * @receiver The maximum selectable [LocalDate].
 * @return A [SelectableDates] object restricting selectable dates.
 */
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

/**
 * Converts the [LocalDate] to epoch milliseconds at the start of the day UTC.
 *
 * Requires API level 26 or higher.
 *
 * @receiver The [LocalDate] to convert.
 * @return Epoch milliseconds representing the start of the day UTC.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLong() = this
    .atStartOfDay((ZoneOffset.UTC)) // Convert to ZonedDateTime
    .toInstant()                          // Convert to Instant
    .toEpochMilli()                       // Convert to epoch millis

/**
 * Calculates the age in years from this [LocalDate] to the current date.
 *
 * Requires API level 26 or higher.
 *
 * @receiver The birthdate or starting [LocalDate].
 * @return The number of full years elapsed since this date.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.getAge(): Int = Period.between(this, LocalDate.now()).years