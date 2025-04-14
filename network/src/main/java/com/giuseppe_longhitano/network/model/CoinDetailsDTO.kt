package com.giuseppe_longhitano.network.model

data class CoinDetailsDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val description: Map<String, String>,
)