package com.example.vitesse.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Currency (
    val eur : LocalCurrency = LocalCurrency(0.0),
    val usd : LocalCurrency = LocalCurrency(0.0),
)

@Serializable
data class LocalCurrency(val gbp: Double)