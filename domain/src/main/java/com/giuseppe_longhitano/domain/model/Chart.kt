package com.giuseppe_longhitano.domain.model

data class Chart(
    val itemsChart: List<ChartItem> = emptyList(),
    val interval: String = "",
)

data class ChartItem(val title: String, val coords: Coordinates)

typealias Coordinates = List<List<Double>>