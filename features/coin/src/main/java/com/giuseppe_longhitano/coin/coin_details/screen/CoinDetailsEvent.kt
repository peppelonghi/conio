package com.giuseppe_longhitano.coin.coin_details.screen

import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.widget.chart.Interval

sealed class CoinDetailsEvent : UIEvent {

    data class OnIntervalChange(val interval: Interval) : CoinDetailsEvent()
    data object RefreshGraph : CoinDetailsEvent()

}