package com.example.vitesse.data

import android.content.Context
import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.repository.ApplicantRepository
import com.example.vitesse.data.repository.CurrencyRepository
import com.example.vitesse.data.repository.WebCurrencyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import utils.AndroidLogger
import utils.CURRENCY_API_FALLBACK_URL
import utils.CURRENCY_API_URL

interface AppContainer {
    val applicantRepository: ApplicantRepository
    val currencyRepository: CurrencyRepository
}

class AppDatabaseContainer(
    private val context: Context,
    private val applicationScope: CoroutineScope
) : AppContainer {

    // applicant repository to get data from ROOM database
    override val applicantRepository: ApplicantRepository by lazy {
        ApplicantRepository(
            applicantDao = VitesseDatabase.getInstance(context, applicationScope).applicantDao(),
            logger = AndroidLogger
        )
    }

    // currency repository to get data from currency API
    private val json = Json { ignoreUnknownKeys = true }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val primaryRetrofit = createRetrofit(CURRENCY_API_URL)
    private val secondaryRetrofit = createRetrofit(CURRENCY_API_FALLBACK_URL)

    private val primaryService: CurrencyApi by lazy { primaryRetrofit.create(CurrencyApi::class.java) }
    private val fallbackService: CurrencyApi by lazy { secondaryRetrofit.create(CurrencyApi::class.java) }

    override val currencyRepository: CurrencyRepository by lazy {
        WebCurrencyRepository(
            primaryService = primaryService,
            fallbackService = fallbackService)
    }
}