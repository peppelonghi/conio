package com.giuseppe_longhitano.coin.coin_list.screen

import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.CommonEvent.Retry
import com.giuseppe_longhitano.arch.event.NavigationEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.routing.RouteScreen
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.BaseViewModel
import com.giuseppe_longhitano.ui.utils.getData
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "CoinListViewModel"

class CoinListViewModel(private val repository: CoinRepository) :
    BaseViewModel<ListModel<Coin>>() {

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
            val previousData = uiState.value.copy(isLoading = true, error = null).data
            val pageToFetch = previousData?.page ?: 1
            _uiState.update { currentState ->
                val isInitialLoad = currentState.data?.items.isNullOrEmpty() && currentState.error == null
                val updatedDataModel = currentState.data?.copy(
                    isItemsLoading = !isInitialLoad,
                    error = null
                )
                currentState.copy(
                    isLoading = isInitialLoad,
                    data = updatedDataModel,
                    error = null
                )
            }
            repository.getCoin(pageToFetch).collect(::handleCoinsResult)
        }
    }

    private fun handleCoinsResult(coinResult: Result<List<Coin>>) {
        val currentData = _uiState.getData()
        coinResult.fold(onSuccess = { newCoins ->
            val updatedItems = currentData?.items.orEmpty() + newCoins
            val pageToFetch = (currentData?.page ?: 0) + 1
            val endReached = newCoins.isEmpty()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    data = ListModel(
                        items = updatedItems,
                        page = pageToFetch,
                        endReached = endReached,
                        isItemsLoading = false
                    ),
                    error = null
                )
            }
         }, onFailure = {th->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    data = if (currentData?.items.isNullOrEmpty()) null else currentData?.copy(isItemsLoading = false),
                    error = th
                )
            }
        })
    }

    override fun handleEvent(uiEvent: UIEvent) {

        when (uiEvent) {

            is CoinListEvent.CoinClicked -> super.handleEvent(NavigationEvent(RouteScreen.CoinDetailScreen(uiEvent.id.value)))

            is CommonEvent.Next, is Retry -> getCoins()

            else -> super.handleEvent(uiEvent)
        }


    }
}