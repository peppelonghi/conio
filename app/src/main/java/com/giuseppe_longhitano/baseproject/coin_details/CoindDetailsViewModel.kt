package com.giuseppe_longhitano.baseproject.coin_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.giuseppe_longhitano.baseproject.routing.RouteScreen
import com.giuseppe_longhitano.domain.repositories.CoinRepository

private const val TAG = "CoindDetailsViewModel"

class CoindDetailsViewModel(
    private val repository: CoinRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val coindId = savedStateHandle.toRoute<RouteScreen.CoinDetailScreen>().id

    init {
        Log.d(TAG, "null() $coindId")
    }
}