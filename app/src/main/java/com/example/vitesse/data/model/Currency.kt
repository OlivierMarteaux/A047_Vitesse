package com.example.vitesse.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRate (
    val eur : LocalCurrency = LocalCurrency(),
    val gbp : LocalCurrency = LocalCurrency(),
)

@Serializable
data class LocalCurrency(
    val gbp: Double = 0.0,
    val eur: Double = 0.0
)