package com.example.vitesse.data.repository

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.ExchangeRate
import utils.debugLog

interface CurrencyRepository {
    suspend fun getEurExchangeRate(): ExchangeRate
    suspend fun getGbpExchangeRate(): ExchangeRate
}

class WebCurrencyRepository(
    private val primaryService: CurrencyApi,
    private val fallbackService: CurrencyApi
) : CurrencyRepository {

    override suspend fun getEurExchangeRate(): ExchangeRate {
        return try {
            val result = primaryService.getEurExchangeRate()
            debugLog("Primary exchange rate Api call successful")
            result
        } catch (e: Exception) {
            debugLog("Primary failed, falling back. Reason: ${e.message}")
            try {
                val result = fallbackService.getEurExchangeRate()
                debugLog("Fallback exchange rate Api call successful")
                result
            } catch (e2: Exception) {
                debugLog("Fallback failed. Reason: ${e2.message}")
                debugLog("Fallback to default exchange rates")
                ExchangeRate()
            }
        }
    }

    override suspend fun getGbpExchangeRate(): ExchangeRate {
        return try {
            val result = primaryService.getGbpExchangeRate()
            debugLog("Primary exchange rate Api call successful")
            result
        } catch (e: Exception) {
            debugLog("Primary failed, falling back. Reason: ${e.message}")
            try {
                val result = fallbackService.getGbpExchangeRate()
                debugLog("Fallback exchange rate Api call successful")
                result
            } catch (e2: Exception) {
                debugLog("Fallback failed. Reason: ${e2.message}")
                debugLog("Fallback to default exchange rates")
                ExchangeRate()
            }
        }
    }
}