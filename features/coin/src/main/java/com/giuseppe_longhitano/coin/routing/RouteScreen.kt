package com.giuseppe_longhitano.coin.routing


import com.giuseppe_longhitano.arch.routing.Route
import kotlinx.serialization.Serializable

private const val TAG = "RouteScreen"

sealed interface RouteScreen : Route {

    @Serializable
    data object CoinListScreen : RouteScreen

    @Serializable
    data class CoinDetailScreen(val id: String) : RouteScreen

}