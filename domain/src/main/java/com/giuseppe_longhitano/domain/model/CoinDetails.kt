package com.giuseppe_longhitano.domain.model



data class CoinDetails(
    val coin: Coin? = null,
    val description: String = "",
    val url: Url = Url.NotValidUrl()
)

