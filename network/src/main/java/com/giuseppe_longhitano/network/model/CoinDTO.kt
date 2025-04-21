package com.giuseppe_longhitano.network.model

 import kotlinx.serialization.SerialName
 import kotlinx.serialization.Serializable


@Serializable
data class CoinDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price")
    val currentPrice: Double
)