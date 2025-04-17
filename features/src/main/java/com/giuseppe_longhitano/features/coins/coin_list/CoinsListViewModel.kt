package com.giuseppe_longhitano.features.coins.coin_list

import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "CoinViewModel"

class CoinsListViewModel(private val repository: CoinRepository) : ConioBaseViewModel<List<Coin>>(initialData = emptyList()) {

   override val uiState = _uiState.onStart {
        getCoins()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = UIState<List<Coin>>(
            isLoading = true,
            data = emptyList()
        )
    )

    override fun handleEvent(uiEvent: UIEvent) {
        when(uiEvent) {
            is CommonEvent.Retry-> getCoins()
        }
    }


    private fun getCoins() {
        _uiState.value = UIState(data = _uiState.value.data, isLoading = true, error = null)
        viewModelScope.launch {
            repository.getCoin().collect {
                it.fold(onSuccess = { items ->
                    _uiState.value =
                        UIState<List<Coin>>(data = items, isLoading = false, error = null)
                }, onFailure = {
                    _uiState.value =
                        UIState(data = _uiState.value.data, isLoading = false, error = it)
                })
            }
        }
    }


}


