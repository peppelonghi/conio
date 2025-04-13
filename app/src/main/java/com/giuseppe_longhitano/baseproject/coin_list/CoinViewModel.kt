package com.giuseppe_longhitano.baseproject.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "CoinViewModel"

class CoinViewModel(private val repository: CoinRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState<List<Coin>>>(
        UIState<List<Coin>>(
            isLoading = true,
            data = emptyList()
        )
    )
    val uiState = _uiState.onStart {
        getCoins()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = UIState<List<Coin>>(
            isLoading = true,
            data = emptyList()
        )
    )


    private fun getCoins() {
        viewModelScope.launch {
            repository.getCoin().collect {
                it.fold(onSuccess = { items ->
                    _uiState.value =
                         UIState<List<Coin>>(data = items, isLoading = false, error = null)
                }, onFailure = {
                    _uiState.value =
                        UIState(data = _uiState.value.data, isLoading = false, error = it)
                    Log.d(TAG, "getCoins() called errore ${it.message}")
                })
            }
        }
    }

    fun handleEvent() {

    }
}


