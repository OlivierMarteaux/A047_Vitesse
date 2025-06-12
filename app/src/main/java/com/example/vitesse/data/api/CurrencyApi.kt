package com.example.vitesse.data.api

import com.example.vitesse.data.model.Currency
import retrofit2.http.GET

interface CurrencyApi {
    @GET("currencies/eur.json")
    suspend fun getCurrency(): Currency
}