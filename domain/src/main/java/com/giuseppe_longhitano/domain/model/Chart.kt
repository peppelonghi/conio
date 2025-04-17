package com.giuseppe_longhitano.domain.model

data class Chart(
    val listChartItems: List<ChartItem> = emptyList(),
    val dayInterval: String,
    val hourInterval: String
)

data class ChartItem(val title: String, val item: List<List<Double>>)