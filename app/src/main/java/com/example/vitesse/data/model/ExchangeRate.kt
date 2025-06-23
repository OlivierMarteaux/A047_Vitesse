package com.example.vitesse.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRate (
    val eur : LocalCurrency = LocalCurrency(eur = 1.0, gbp = 0.85690067),
    val gbp : LocalCurrency = LocalCurrency(gbp = 1.0, eur = 1.16779129),
)

@Serializable
data class LocalCurrency(
    val gbp: Double = 0.0,
    val eur: Double = 0.0
)