package com.giuseppe_longhitano.repositories.utils

import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
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
        id = Id(this.id),
        symbol = this.symbol,
        name = name,
        description = description.values.firstOrNull()?:""
    )
