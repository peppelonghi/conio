package com.giuseppe_longhitano.coin.coin_details.screen

import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.utils.DayInterval
import com.giuseppe_longhitano.coin.utils.HourInterval

sealed class CoinDetailsEvent: UIEvent {
    data class OnIntervalChange(val hourInterval: HourInterval): CoinDetailsEvent()
    data class OnDaysChange(val dayInterval: DayInterval): CoinDetailsEvent()
    data object RefreshGraph : CoinDetailsEvent()

}