package com.giuseppe_longhitano.coin.coin_details.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.coin.routing.RouteScreen
import com.giuseppe_longhitano.coin.utils.DayInterval
import com.giuseppe_longhitano.coin.utils.HourInterval
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.ui_model.UIState
import com.giuseppe_longhitano.ui.utils.getData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "CoinDetailsViewModel"

class CoinDetailsViewModel(
    private val repository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ConioBaseViewModel<ExpandedCoinDetails>(null) {

    private val coindId = Id(savedStateHandle.toRoute<RouteScreen.CoinDetailScreen>().id)
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
        hourInterval: HourInterval = HourInterval.THIRTY_MINUTES,
        dayInterval: DayInterval = DayInterval.THIRTY_DAYS
    ) {
        val chart = uiState.getData()?.chart?.copy(isLoading = true, error = null) ?: UIState(
            data =
                Chart(
                    listChartItems = emptyList(),
                    hourInterval = hourInterval.value,
                    dayInterval = dayInterval.value
                ), isLoading = true, error = null
        )
        _uiState.value = uiState.value.copy(data = uiState.getData()?.copy(chart = chart))
        job?.cancel()
        job= viewModelScope.launch {
             repository.getChart(
                id = coindId,
                hourInterval = hourInterval.value,
                dayInterval = dayInterval.value
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
                    hourInterval = HourInterval.THIRTY_MINUTES.value,
                    dayInterval = DayInterval.THIRTY_DAYS.value
                )
            ) { coinDetails, chart ->
                handleResultDetails(coinDetails, chart)
            }.collect()
        }
    }

    private fun handleResultChart(graphResult: Result<Chart>) {
        graphResult.fold(onSuccess = {
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
                hourInterval = HourInterval.Companion.safeValueOf(uiState.getData()?.chart?.data?.hourInterval),
                dayInterval = DayInterval.Companion.safeValueOf(uiState.getData()?.chart?.data?.dayInterval)
            )

            is CoinDetailsEvent.OnIntervalChange ->
                getChartData(
                    hourInterval = uiEvent.hourInterval,
                    dayInterval = DayInterval.Companion.safeValueOf(
                        uiState.getData()?.chart?.data?.dayInterval
                            ?: DayInterval.THIRTY_DAYS.value
                    )
                )

            is CoinDetailsEvent.OnDaysChange ->
                getChartData(
                    dayInterval = uiEvent.dayInterval,
                    hourInterval = HourInterval.Companion.safeValueOf(
                        uiState.value.data?.chart?.data?.hourInterval
                            ?: HourInterval.THIRTY_MINUTES.value
                    )
                )

        }

    }
}

