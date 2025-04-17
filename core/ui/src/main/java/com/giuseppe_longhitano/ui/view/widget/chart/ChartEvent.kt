package com.giuseppe_longhitano.ui.view.widget.chart

import com.giuseppe_longhitano.arch.event.UIEvent

sealed class ChartEvent: UIEvent {
    data class OnIntervalChange(val hourInterval: String): ChartEvent()
    data class OnDaysChange(val dayInterval: String): ChartEvent()

}