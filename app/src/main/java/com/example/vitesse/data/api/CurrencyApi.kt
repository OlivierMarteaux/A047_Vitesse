package com.example.vitesse.data.api

import com.example.vitesse.data.model.ExchangeRate
import retrofit2.http.GET

interface CurrencyApi {
    @GET("eur.json")
    suspend fun getEurExchangeRate(): ExchangeRate
    @GET("gbp.json")
    suspend fun getGbpExchangeRate(): ExchangeRate
}