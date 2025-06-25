package com.example.vitesse.extensions

import extensions.toEurString
import extensions.toGbpString
import extensions.toLocalCurrencyString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Locale

class DoubleExtensionsTest {

    @Test
    fun `toLocalCurrencyString should format correctly`() {
        val value = 1234.0
        val formatted = value.toLocalCurrencyString(Locale.FRANCE)
        assertTrue(formatted.contains("€"))
    }

    @Test
    fun `toGbpString should return correct format`() {
        val result = 1234.56.toGbpString()
        assertEquals("£ 1234,56", result)
    }

    @Test
    fun `toEurString should return correct format`() {
        val result = 789.1.toEurString()
        assertEquals("€ 789,1", result)
    }
}