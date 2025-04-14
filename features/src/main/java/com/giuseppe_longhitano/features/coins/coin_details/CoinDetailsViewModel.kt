package com.giuseppe_longhitano.features.coins.coin_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.giuseppe_longhitano.arch.UIEvent
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.features.coins.routing.RouteScreen
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "CoindDetailsViewModel"

class CoinDetailsViewModel(
    private val repository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ConioBaseViewModel<CoinDetails>(null) {

    private val coindId = Id(savedStateHandle.toRoute<RouteScreen.CoinDetailScreen>().id)

    override val uiState = _uiState.onStart {
        getCoinDetails()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = UIState<CoinDetails>(
            isLoading = true,
            data = null
        )
    )

    private fun getCoinDetails() {
        viewModelScope.launch {
            repository.getCoinDetails(coindId).collect {
                it.fold(onSuccess = {
                    _uiState.value =
                        UIState<CoinDetails>(data = it, isLoading = false, error = null)
                }, onFailure = {
                    _uiState.value =
                        UIState<CoinDetails>(data = null, isLoading = false, error = it)
                })
            }
        }
    }

    override fun handleEvent(uiEvent: UIEvent) {

    }
}