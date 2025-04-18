package com.giuseppe_longhitano.coin.coin_list.screen

import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CoinsListViewModel(private val repository: CoinRepository) :
    ConioBaseViewModel<List<Coin>>() {

    override val uiState = _uiState.onStart {
        getCoins()
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000L),
        initialValue = UIState<List<Coin>>(
            isLoading = true,
            data = emptyList()
        )
    )

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is CommonEvent.Retry -> getCoins()
        }
    }


    private fun getCoins() {
        viewModelScope.launch {
             repository.getCoin().collect {
                it.fold(onSuccess = { data ->
                    _uiState.value =
                        _uiState.value.copy(data = data, isLoading = false, error = null)
                }, onFailure = {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it)
                })
            }
        }
    }


}