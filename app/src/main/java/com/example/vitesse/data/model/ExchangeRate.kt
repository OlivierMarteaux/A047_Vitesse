package com.example.vitesse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRate (
    @SerialName("eur") val fromEur : LocalCurrency = LocalCurrency(toEur = 1.0, toGbp = 0.85690067),
    @SerialName("gbp") val fromGbp : LocalCurrency = LocalCurrency(toGbp = 1.0, toEur = 1.16779129),
    val staticFallback: Boolean = false
)

@Serializable
data class LocalCurrency(
    @SerialName("gbp") val toGbp: Double = 0.0,
    @SerialName("eur") val toEur: Double = 0.0
)