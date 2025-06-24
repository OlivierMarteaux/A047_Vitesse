package com.example.vitesse.data.api

import com.example.vitesse.data.model.ExchangeRate
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("{currency}.json")
    suspend fun getExchangeRate(@Path("currency") currency: String): ExchangeRate
}
