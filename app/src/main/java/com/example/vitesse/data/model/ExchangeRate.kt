package com.example.vitesse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents currency exchange rate data between EUR and GBP.
 *
 * This class is intended to be deserialized from a JSON structure where the top-level keys are `"eur"` and `"gbp"`,
 * each mapping to a [LocalCurrency] object describing conversion rates from that currency.
 *
 * @property fromEur Conversion rates from EUR to other currencies (e.g., GBP).
 * @property fromGbp Conversion rates from GBP to other currencies (e.g., EUR).
 * @property staticFallback Indicates whether the exchange rate data is static (used as a fallback
 * when live data is unavailable).
 */
@Serializable
data class ExchangeRate (
    @SerialName("eur") val fromEur : LocalCurrency = LocalCurrency(toEur = 1.0, toGbp = 0.85690067),
    @SerialName("gbp") val fromGbp : LocalCurrency = LocalCurrency(toGbp = 1.0, toEur = 1.16779129),
    val staticFallback: Boolean = false
)

/**
 * Represents conversion rates from a given local currency to other currencies.
 *
 * Typically used in the context of EUR and GBP conversions.
 * Fields are annotated with [SerialName] to map correctly from the serialized JSON keys.
 *
 * @property toGbp Conversion rate from the current currency to GBP.
 * @property toEur Conversion rate from the current currency to EUR.
 */
@Serializable
data class LocalCurrency(
    @SerialName("gbp") val toGbp: Double = 0.0,
    @SerialName("eur") val toEur: Double = 0.0
)