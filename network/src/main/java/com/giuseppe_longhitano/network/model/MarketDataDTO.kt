package com.giuseppe_longhitano.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketDataDTO(@SerialName("current_price") val currentPrice: Map<String, Double>)