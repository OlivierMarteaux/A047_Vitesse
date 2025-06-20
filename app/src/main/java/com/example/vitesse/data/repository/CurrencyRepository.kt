package com.example.vitesse.data.repository

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.ExchangeRate

interface CurrencyRepository {
    suspend fun getEurExchangeRate(): ExchangeRate
    suspend fun getGbpExchangeRate(): ExchangeRate
}

class WebCurrencyRepository(private val currencyApi: CurrencyApi) : CurrencyRepository {
    override suspend fun getEurExchangeRate(): ExchangeRate {
        return currencyApi.getEurExchangeRate()
    }
    override suspend fun getGbpExchangeRate(): ExchangeRate {
        return currencyApi.getGbpExchangeRate()
    }
}