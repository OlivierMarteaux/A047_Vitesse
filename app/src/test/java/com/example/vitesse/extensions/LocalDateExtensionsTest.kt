package com.example.vitesse.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import extensions.getAge
import extensions.toLocalDate
import extensions.toLocalDateString
import extensions.toLong
import extensions.upTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class LocalDateExtensionsTest {

    @Test
    fun `toLocalDateString should format in French`() {
        Locale.setDefault(Locale.FRANCE)
        val date = LocalDate.of(2000, 1, 2)
        assertEquals("02/01/2000", date.toLocalDateString())
    }

    @Test
    fun `toLocalDateString should format in English`() {
        Locale.setDefault(Locale.UK)
        val date = LocalDate.of(2000, 1, 2)
        assertEquals("01/02/2000", date.toLocalDateString())
    }

    @Test
    fun `upTo should return false if date is after`() {
        val now = LocalDate.now()
        val selectableDates = now.upTo()
        val futureMillis = now.plusDays(1).toLong()
        assertFalse(selectableDates.isSelectableDate(futureMillis))
    }

    @Test
    fun `upTo should return true if date is before or same`() {
        val now = LocalDate.now()
        val selectableDates = now.upTo()
        val todayMillis = now.toLong()
        assertTrue(selectableDates.isSelectableDate(todayMillis))
    }

    @Test
    fun `toLong should convert correctly`() {
        val date = LocalDate.of(2020, 1, 1)
        val millis = date.toLong()
        assertEquals(date, millis.toLocalDate())
    }

    @Test
    fun `getAge should return correct age`() {
        val birthday = LocalDate.now().minusYears(30)
        val age = birthday.getAge()
        assertEquals(30, age)
    }

    @Test
    fun `toLocalDate should convert millis to date`() {
        val date = LocalDate.of(2022, 5, 10)
        val millis = date.toLong()
        val result = millis.toLocalDate()
        assertEquals(date, result)
    }
}
