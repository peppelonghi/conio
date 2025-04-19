package com.giuseppe_longhitano.coin.coin_details.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.coin.routing.RouteScreen.CoinDetailScreen
import com.giuseppe_longhitano.ui.view.widget.chart.Interval
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.utils.getData
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "CoinDetailsViewModel"

class CoinDetailsViewModel(
    private val repository: CoinRepository,
    savedStateHandle: SavedStateHandle,
) : ConioBaseViewModel<ExpandedCoinDetails>(ExpandedCoinDetails(CoinDetails())) {

    //  private val coindId = Id(savedStateHandle.toRoute<RouteScreen.CoinDetailScreen>().id)
    private val coindId = Id(savedStateHandle.get<String>(CoinDetailScreen::id.name).orEmpty())
    private var job: Job? = null

    override val uiState = _uiState.onStart {
        getCoinDetails()
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000L),
        initialValue = UIState<ExpandedCoinDetails>(
            isLoading = true,
            data = null
        )
    )

    private fun getChartData(
        interval: Interval = Interval.entries.first(),
     ) {

        val chart = uiState.getData()?.chart?.copy(isLoading = true, error = null) ?: UIState(
            data =
                Chart(
                    listChartItems = emptyList(),
                    interval = interval.value,
                 ), isLoading = true, error = null
        )
        _uiState.value = uiState.value.copy(data = uiState.getData()?.copy(chart = chart))
        job?.cancel()
        job = viewModelScope.launch {
            repository.getChart(
                id = coindId,
                interval = interval.value,
             ).collect(::handleResultChart)
        }
    }

    private fun getCoinDetails() {
        job?.cancel()
        job = viewModelScope.launch {
            combine(
                repository.getCoinDetails(coindId),
                repository.getChart(
                    id = coindId,
                    interval = Interval.entries.first().value,
                 )
            ) { coinDetails, chart ->
                handleResultDetails(coinDetails, chart)
            }.collect()
        }
    }
    var prova: List<List<Double>> = emptyList()

     private fun handleResultChart(graphResult: Result<Chart>) {
        graphResult.fold(onSuccess = {
            Log.d(TAG, "handleResultChart() called with: it = ${it.listChartItems.first().item}")
            prova = it.listChartItems.first().item
             val current =
                uiState.getData()?.copy(chart = UIState(data = it, isLoading = false, error = null))
            _uiState.value = uiState.value.copy(data = current, isLoading = false, error = null)
        }, onFailure = {
            val current =
                uiState.getData()?.copy(chart = UIState(data = null, isLoading = false, error = it))
            _uiState.value = uiState.value.copy(data = current, isLoading = false, error = null)
        })
    }


    private fun handleResultDetails(
        coinDetailsResult: Result<CoinDetails>,
        graphResult: Result<Chart>
    ) {
        when {
            coinDetailsResult.isFailure -> _uiState.value = uiState.value.copy(
                data = null,
                isLoading = false,
                error = coinDetailsResult.exceptionOrNull()
            )

            coinDetailsResult.isSuccess && graphResult.isFailure ->
                _uiState.value = uiState.value.copy(
                    data = ExpandedCoinDetails(
                        coinDetailsResult.getOrThrow(),
                        UIState(
                            data = null,
                            isLoading = true,
                            error = graphResult.exceptionOrNull()
                        )
                    ),
                    isLoading = false,
                    error = null
                )

            coinDetailsResult.isFailure && graphResult.isSuccess ->
                _uiState.value = uiState.value.copy(
                    data = null,
                    isLoading = false,
                    error = coinDetailsResult.exceptionOrNull()
                )

            coinDetailsResult.isSuccess && graphResult.isSuccess ->
                _uiState.value = uiState.value.copy(
                    data = ExpandedCoinDetails(
                        coinDetailsResult.getOrThrow(),
                        UIState(data = graphResult.getOrThrow(), isLoading = false, error = null)
                    ), isLoading = false, error = null
                )
        }
    }

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is CommonEvent.Retry -> getCoinDetails()

            is CoinDetailsEvent.RefreshGraph -> getChartData(
                interval = Interval.Companion.safeValueOf(uiState.getData()?.chart?.data?.interval),
             )

            is CoinDetailsEvent.OnIntervalChange ->
                getChartData(
                    interval = uiEvent.interval,

                )
            else -> throw Throwable("Event not handled")
        }

    }
}

