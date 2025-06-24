package com.example.vitesse

import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.model.LocalCurrency
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class CurrencyApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: CurrencyApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true } // configure as needed

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        api = retrofit.create(CurrencyApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun currencyApi_getExchangeRate_returnsCorrectRates() = runTest {
        val mockJson = """
            {
                "date": "2025-06-23",
                "eur": 
                    {
                        "eur": 1.0,
                        "gbp": 0.85690067
                    }
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockJson).setResponseCode(200))

        val result = api.getExchangeRate("eur")

        assertEquals(LocalCurrency(toGbp=0.85690067, toEur=1.0), result.fromEur)
        assertEquals(1.0, result.fromEur.toEur, 0.0)
        assertEquals(0.85690067, result.fromEur.toGbp, 0.0)
    }
}