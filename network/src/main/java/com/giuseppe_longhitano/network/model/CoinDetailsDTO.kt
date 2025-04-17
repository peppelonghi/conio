package com.giuseppe_longhitano.network.model

import com.google.gson.annotations.SerializedName

data class CoinDetailsDTO(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("image")
    val image:Images,
    val description: Map<String, String>,
)

data class Images(val thumb: String, val small: String, val large: String)