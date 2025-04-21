package com.giuseppe_longhitano.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailsDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: Images,
    val description: Map<String, String>,
    @SerialName("market_data")
    val marketData: MarketData,
)

@Serializable
data class Images(val thumb: String, val small: String, val large: String)

@Serializable
data class MarketData(@SerialName("current_price") val currentPrice: Map<String, Double>)