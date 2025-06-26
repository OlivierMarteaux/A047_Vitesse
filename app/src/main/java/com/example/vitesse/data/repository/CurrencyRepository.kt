package com.example.vitesse.data.repository

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.ExchangeRate
import com.example.vitesse.data.model.LocalCurrency
import utils.Logger

/**
 * Repository interface for retrieving currency exchange rate data.
 */
interface CurrencyRepository {
    /**
     * Retrieves the exchange rate data for a given base currency.
     *
     * @param currency The currency code (e.g., "eur" or "gbp") used to query exchange rates.
     * @return An [ExchangeRate] object containing conversion rates to and from the specified currency.
     * @throws Exception If the data source fails and no fallback is available.
     */
    suspend fun getExchangeRate(currency: String): ExchangeRate
}

/**
 * Implementation of [CurrencyRepository] that fetches exchange rates using two [CurrencyApi] services:
 * a primary source and a fallback source. If both services fail, returns a static default value.
 *
 * @property primaryService The primary [CurrencyApi] service used for exchange rate retrieval.
 * @property fallbackService The fallback [CurrencyApi] service used if the primary fails.
 * @property logger A [Logger] used to log API success, failure, and fallback usage.
 */
class WebCurrencyRepository(
    private val primaryService: CurrencyApi,
    private val fallbackService: CurrencyApi,
    private val logger: Logger
) : CurrencyRepository {

    /**
     * Attempts to retrieve exchange rates from the primary service. If it fails, the fallback service is used.
     * If both fail, a static default exchange rate is returned and marked with `staticFallback = true`.
     *
     * @param currency The currency code used in the API path (e.g., "eur", "gbp").
     * @return An [ExchangeRate] object, either from the API(s) or a predefined static fallback.
     */
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