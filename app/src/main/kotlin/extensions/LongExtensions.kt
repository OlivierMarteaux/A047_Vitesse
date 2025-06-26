package extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/**
 * Converts epoch milliseconds [Long] to a [LocalDate] in the system default timezone.
 *
 * Requires API level 26 or higher.
 *
 * @receiver The epoch milliseconds to convert.
 * @return The corresponding [LocalDate].
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDate(): LocalDate = Instant
    .ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .toLocalDate()