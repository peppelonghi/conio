package com.giuseppe_longhitano.features.coins.routing

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.ui.ui_model.TopAppBarState
import kotlinx.serialization.Serializable

private const val TAG = "RouteScreen"
sealed interface RouteScreen : Route {
    @Serializable
    object CoinListScreen : RouteScreen

    @Serializable
    data class CoinDetailScreen(val id: String) : RouteScreen

}