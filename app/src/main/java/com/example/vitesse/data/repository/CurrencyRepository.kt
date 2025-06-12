package com.example.vitesse.data.repository

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.Currency

interface CurrencyRepository {
    suspend fun getCurrency(): Currency
}

class WebCurrencyRepository(private val currencyApi: CurrencyApi) : CurrencyRepository {
    override suspend fun getCurrency(): Currency {
        return currencyApi.getCurrency()
    }
}