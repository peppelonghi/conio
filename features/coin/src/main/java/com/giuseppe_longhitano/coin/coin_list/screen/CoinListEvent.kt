package com.giuseppe_longhitano.coin.coin_list.screen

import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.domain.model.Id

sealed interface CoinListEvent: UIEvent {
    data class ClickedCoin(val id: Id): CoinListEvent
}