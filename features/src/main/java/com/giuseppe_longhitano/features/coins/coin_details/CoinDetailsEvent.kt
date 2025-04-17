package com.giuseppe_longhitano.features.coins.coin_details

import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.features.coins.utils.DayInterval
import com.giuseppe_longhitano.features.coins.utils.HourInterval

sealed class CoinDetailsEvent: UIEvent{
    data class OnIntervalChange(val hourInterval: HourInterval): CoinDetailsEvent()
    data class OnDaysChange(val dayInterval: DayInterval): CoinDetailsEvent()
    data object RefreshGraph : CoinDetailsEvent()

}