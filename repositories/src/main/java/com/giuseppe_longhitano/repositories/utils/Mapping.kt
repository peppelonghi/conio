package com.giuseppe_longhitano.repositories.utils

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.ChartItem
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.model.Url
import com.giuseppe_longhitano.network.model.ChartResponseDTO
import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.network.model.CoinDetailsDTO

internal fun CoinDTO.toDomain() =
    Coin(
        id = Id(this.id),
        urlmage = this.image,
        symbol = this.symbol,
        currentPrice = currentPrice,
        name = name
    )

internal fun CoinDetailsDTO.toDomain() =
    CoinDetails(
        coin = Coin(
            id = Id(this.id),
            symbol = this.symbol,
            name = name,
            urlmage = this.image.large,
            currentPrice = this.marketDataDTO.currentPrice[EUR] ?: 0.0
        ),
        description = description.values.firstOrNull() ?: "",
        url = Url.createUrl(links.homepage.firstOrNull())
    )

internal fun ChartResponseDTO.toDomain() = Chart(
    interval = "",
    itemsChart = listOf(
        ChartItem(title = PRICES, coords = this.prices),
        ChartItem(title = MarketCaps, coords = this.marketCaps),
        //NON SO CHE RAPPRESNETANO PER CUI LO COMMENTO
        //ChartItem(title = TotalVolume, item = this.total_volumes)
    )


)
