package com.giuseppe_longhitano.repositories.utils

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.ChartItem
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.network.model.ChartResponseDTO
import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.network.model.CoinDetailsDTO

internal fun CoinDTO.toCoin() =
    Coin(
        id = Id(this.id),
        urlmage = this.image,
        symbol = this.symbol,
        currentPrice = currentPrice,
        name = name
    )

internal fun CoinDetailsDTO.toCoinDetails() =
    CoinDetails(
        coin = Coin(
            id = Id(this.id),
            symbol = this.symbol,
            name = name,
            urlmage = this.image.large,
            currentPrice = 0.0
        ),
        description = description.values.firstOrNull() ?: "",
    )

internal fun ChartResponseDTO.toChart() = Chart(
    hourInterval = "",
    dayInterval = "",
    listChartItems = listOf(
        ChartItem(title = "Prices", item = this.prices),
        ChartItem(title = "MarketCaps", item = this.market_caps),
        ChartItem(title = "TotalVolume", item = this.total_volumes)
    )


)
