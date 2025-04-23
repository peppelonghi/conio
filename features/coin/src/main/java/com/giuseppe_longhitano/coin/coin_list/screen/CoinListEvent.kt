package com.giuseppe_longhitano.coin.coin_list.screen

import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.domain.model.Id

sealed class CoinListEvent : UIEvent {

    data class CoinClicked(val id: Id) : CoinListEvent()

}