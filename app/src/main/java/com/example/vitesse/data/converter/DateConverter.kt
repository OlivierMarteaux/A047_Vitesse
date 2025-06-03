package com.example.vitesse.data.converter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * Converter class for transforming [LocalDate] objects to and from [Long] timestamps.
 *
 * Used by Room to persist date-time data in a format that SQLite can store.
 */
class DateConverter {
    /**
     * Converts a [Long] timestamp to a [LocalDate] in UTC.
     *
     * @param timestamp The timestamp in seconds since the epoch.
     * @return A [LocalDate] instance, or `null` if [timestamp] is null.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(timestamp: Long?): LocalDate? {
        return timestamp?.let {
            LocalDate.ofEpochDay(it)
        }
    }
    /**
     * Converts a [LocalDate] to a [Long] timestamp in seconds.
     *
     * @param localDate The date-time to convert.
     * @return The number of seconds since epoch, or `null` if [localDate] is null.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromlocalDate(localDate: LocalDate?): Long? {
        return localDate?.toEpochDay()
    }
}