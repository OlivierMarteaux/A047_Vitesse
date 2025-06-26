package com.example.vitesse.data.api

import com.example.vitesse.data.model.ExchangeRate
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for fetching currency exchange rates.
 */
interface CurrencyApi {
    /**
     * Retrieves the exchange rate for the given currency code.
     *
     * @param currency The currency code (e.g., "usd", "eur") used to construct the API endpoint.
     *                 The value is inserted into the URL path as `{currency}.json`.
     * @return An [ExchangeRate] object containing exchange rate data for the specified currency.
     *
     * @throws retrofit2.HttpException If the request fails due to a non-2xx HTTP response.
     * @throws java.io.IOException If a network error occurs.
     */
    @GET("{currency}.json")
    suspend fun getExchangeRate(@Path("currency") currency: String): ExchangeRate
}
