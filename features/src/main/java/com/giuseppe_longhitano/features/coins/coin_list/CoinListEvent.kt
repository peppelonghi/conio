package com.giuseppe_longhitano.features.coins.coin_list

import com.giuseppe_longhitano.arch.UIEvent
import com.giuseppe_longhitano.domain.model.Id

sealed interface CoinListEvent: UIEvent {
    data class ClickedCoin(val id: Id): CoinListEvent
}