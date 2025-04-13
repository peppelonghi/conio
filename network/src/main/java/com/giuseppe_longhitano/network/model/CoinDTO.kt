package com.giuseppe_longhitano.network.model

import com.google.gson.annotations.SerializedName


data class CoinDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price")
    val currentPrice: Double
)