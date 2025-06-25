package com.example.vitesse.data.repository

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.ExchangeRate
import com.example.vitesse.data.model.LocalCurrency
import utils.Logger

interface CurrencyRepository {
    suspend fun getExchangeRate(currency: String): ExchangeRate
}

class WebCurrencyRepository(
    private val primaryService: CurrencyApi,
    private val fallbackService: CurrencyApi,
    private val logger: Logger
) : CurrencyRepository {

    override suspend fun getExchangeRate(currency: String): ExchangeRate {
        return try {
            val result = primaryService.getExchangeRate(currency)
            logger.d("Primary exchange rate Api call successful")
            result
        } catch (e: Exception) {
            logger.d("Primary failed, falling back. Reason: ${e.message}")

            try {
                val result = fallbackService.getExchangeRate(currency)
                logger.d("Fallback exchange rate Api call successful")
                result
            } catch (e2: Exception) {
                logger.d("Fallback failed. Reason: ${e2.message}")
                logger.d("Fallback to static default exchange rate value from 06/24/2025. Rates might be outdated.")
                ExchangeRate(
                    fromEur = LocalCurrency(toEur = 1.0, toGbp = 0.85690067),
                    fromGbp = LocalCurrency(toGbp = 1.0, toEur = 1.16779129),
                    staticFallback = true
                )
            }
        }
    }
}