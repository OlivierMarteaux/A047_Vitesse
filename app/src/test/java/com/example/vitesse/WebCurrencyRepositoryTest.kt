package com.example.vitesse

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.ExchangeRate
import com.example.vitesse.data.model.LocalCurrency
import com.example.vitesse.data.repository.WebCurrencyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import utils.NoOpLogger

class WebCurrencyRepositoryTest {

    private lateinit var primaryService: CurrencyApi
    private lateinit var fallbackService: CurrencyApi
    private lateinit var repository: WebCurrencyRepository

    private val expectedRate = ExchangeRate(
        fromEur = LocalCurrency(toEur = 1.0, toGbp = 0.85),
        fromGbp = LocalCurrency(toGbp = 1.0, toEur = 1.17)
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        primaryService = mock(CurrencyApi::class.java)
        fallbackService = mock(CurrencyApi::class.java)
        repository = WebCurrencyRepository(
            primaryService = primaryService,
            fallbackService = fallbackService,
            logger = NoOpLogger
        )
    }

    @Test
    fun `getExchangeRate returns value from primary when successful`() = runTest {
        `when`(primaryService.getExchangeRate("eur")).thenReturn(expectedRate)

        val result = repository.getExchangeRate("eur")

        assertEquals(expectedRate, result)
        verify(primaryService).getExchangeRate("eur")
        verifyNoInteractions(fallbackService)
    }

    @Test
    fun `getExchangeRate returns value from fallback when primary fails`() = runTest {
        `when`(primaryService.getExchangeRate("eur")).thenThrow(RuntimeException("Primary error"))
        `when`(fallbackService.getExchangeRate("eur")).thenReturn(expectedRate)

        val result = repository.getExchangeRate("eur")

        assertEquals(expectedRate, result)
        verify(primaryService).getExchangeRate("eur")
        verify(fallbackService).getExchangeRate("eur")
    }

    @Test
    fun `getExchangeRate returns default when both services fail`() = runTest {
        `when`(primaryService.getExchangeRate("eur")).thenThrow(RuntimeException("Primary error"))
        `when`(fallbackService.getExchangeRate("eur")).thenThrow(RuntimeException("Fallback error"))

        val result = repository.getExchangeRate("eur")

        assertTrue(result.staticFallback)
        assertEquals(1.0, result.fromEur.toEur, 0.0)
        assertEquals(0.85690067, result.fromEur.toGbp, 0.0)
        assertEquals(1.0, result.fromGbp.toGbp, 0.0)
        assertEquals(1.16779129, result.fromGbp.toEur, 0.0)
    }
}