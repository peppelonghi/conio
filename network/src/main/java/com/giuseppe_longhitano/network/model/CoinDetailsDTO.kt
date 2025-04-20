package com.giuseppe_longhitano.network.model

import com.google.gson.annotations.SerializedName

data class CoinDetailsDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: Images,
    val description: Map<String, String>,
    @SerializedName("market_data")
    val marketData: MarketData,
)

data class Images(val thumb: String, val small: String, val large: String)

data class MarketData(@SerializedName("current_price") val currentPrice: Map<String, Double>)