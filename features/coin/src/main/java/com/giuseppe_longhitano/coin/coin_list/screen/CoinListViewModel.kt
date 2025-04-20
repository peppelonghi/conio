package com.giuseppe_longhitano.coin.coin_list.screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.CommonEvent.Retry
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.utils.getData
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "CoinListViewModel2"

class CoinListViewModel(private val repository: CoinRepository) :
    ConioBaseViewModel<ListModel<Coin>>() {

    override val uiState = _uiState.onStart {
        getCoins()
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000L),
        initialValue = UIState<ListModel<Coin>>(
            isLoading = true,
            data = null
        )
    )

    private fun getCoins() {
        viewModelScope.launch {
            var data = _uiState.getData()
            val firstLoading = data?.items.isNullOrEmpty() && _uiState.value.error == null

            if (firstLoading) {
                _uiState.update {
                    it.copy(isLoading = true)
                }
            } else {
                data = data?.copy(isLoading = true, error = null)
                _uiState.update {
                    it.copy(data = data, isLoading = false, error = null)
                }
            }
            repository.getCoin(data?.page ?: 1).collect(::handleCoinsResult)
        }
    }

    private fun handleCoinsResult(coinResult: Result<List<Coin>>) {
        var data = _uiState.getData()
        coinResult.fold(onSuccess = { coins ->
            val newData = ListModel<Coin>(
                isLoading = false,
                error = null,
                items = data?.items.orEmpty() + coins,
                page = (data?.page ?: 1).plus(1),
                endReached = coins.isEmpty()
            )
            _uiState.value = _uiState.value.copy(data = newData, isLoading = false)
         }, onFailure = {

            val isPageLoading =
                if (_uiState.value.isLoading == true) false else _uiState.value.isLoading
            val pageError = if (_uiState.value.isLoading == true) it else _uiState.value.error
            val newData = data?.copy(
                isLoading = false,
                error = pageError ?: it
            )

            Log.d(TAG, "handleCoinsResult() called $it+")
            _uiState.update {
                it.copy(data = newData, isLoading = isPageLoading, error = pageError)
            }
        })
    }

    override fun handleEvent(uiEvent: UIEvent) {

        when (uiEvent) {
            is CommonEvent.Next -> getCoins()
            is Retry -> {
                _uiState.update { it.copy(isLoading = true, error = null) }
                getCoins()
            }

            else -> throw Throwable("No event found for $uiEvent")
        }


    }
}