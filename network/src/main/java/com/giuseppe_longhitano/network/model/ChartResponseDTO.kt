package com.giuseppe_longhitano.network.model

data class ChartResponseDTO(
    val prices: List<List<Double>>,
    val market_caps: List<List<Double>>,
    val total_volumes: List<List<Double>>  )