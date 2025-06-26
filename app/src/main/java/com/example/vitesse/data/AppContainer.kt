package com.example.vitesse.data

import android.content.Context
import com.example.vitesse.data.api.CurrencyApi
import com.example.vitesse.data.repository.CurrencyRepository
import com.example.vitesse.data.repository.LocalApplicantRepository
import com.example.vitesse.data.repository.WebCurrencyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import utils.AndroidLogger
import utils.CURRENCY_API_FALLBACK_URL
import utils.CURRENCY_API_URL

/**
 * Application-level dependency container.
 *
 * Defines the core repositories used across the app, allowing for modular and testable architecture.
 */
interface AppContainer {
    /**
     * Repository that provides access to applicant data from the local Room database.
     */
    val localApplicantRepository: LocalApplicantRepository
    /**
     * Repository that provides currency exchange rate data from remote APIs.
     */
    val currencyRepository: CurrencyRepository
}

/**
 * Implementation of [AppContainer] that provides real instances of repositories using
 * Room database and Retrofit-based APIs.
 *
 * @param context The application context, used to initialize the Room database.
 * @param applicationScope Coroutine scope used for database prepopulation or long-lived operations.
 */
class AppDatabaseContainer(
    private val context: Context,
    private val applicationScope: CoroutineScope
) : AppContainer {

    /**
     * Lazily initialized [LocalApplicantRepository] that fetches data from the Room database.
     */
    override val localApplicantRepository: LocalApplicantRepository by lazy {
        LocalApplicantRepository(
            applicantDao = VitesseDatabase.getInstance(context, applicationScope).applicantDao(),
            logger = AndroidLogger
        )
    }

    /**
     * JSON parser with relaxed rules to ignore unknown keys in API responses.
     */
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Helper function to create a Retrofit instance for the given base URL.
     *
     * @param baseUrl The base URL for the API.
     * @return A configured [Retrofit] instance.
     */
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    /**
     * Retrofit instance for the primary currency API.
     */
    private val primaryRetrofit = createRetrofit(CURRENCY_API_URL)
    /**
     * Retrofit instance for the fallback currency API.
     */
    private val secondaryRetrofit = createRetrofit(CURRENCY_API_FALLBACK_URL)

    /**
     * Lazily initialized primary [CurrencyApi] service.
     */
    private val primaryService: CurrencyApi by lazy { primaryRetrofit.create(CurrencyApi::class.java) }
    /**
     * Lazily initialized fallback [CurrencyApi] service.
     */
    private val fallbackService: CurrencyApi by lazy { secondaryRetrofit.create(CurrencyApi::class.java) }

    /**
     * Lazily initialized [CurrencyRepository] that handles currency data fetching
     * using both primary and fallback APIs.
     */
    override val currencyRepository: CurrencyRepository by lazy {
        WebCurrencyRepository(
            primaryService = primaryService,
            fallbackService = fallbackService,
            logger = AndroidLogger
        )
    }
}