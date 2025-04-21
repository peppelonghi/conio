package com.giuseppe_longhitano.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailsDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: ImagesDTO,
    val description: Map<String, String>,
    @SerialName("market_data")
    val marketDataDTO: MarketDataDTO,
    val links: LinksDTO
)







