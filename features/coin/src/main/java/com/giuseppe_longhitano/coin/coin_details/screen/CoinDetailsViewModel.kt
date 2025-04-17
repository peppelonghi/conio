package com.giuseppe_longhitano.coin.coin_details.screen

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
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.ui.ConioBaseViewModel
import com.giuseppe_longhitano.ui.ui_model.UIState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
    private val repository: CoinRepository,
    savedStateHandle: SavedStateHandle
) : ConioBaseViewModel<ExpandedCoinDetails>(null) {

    private val coindId = Id(savedStateHandle.toRoute<RouteScreen.CoinDetailScreen>().id)

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

        val chart = uiState.value.data?.chart?.copy(isLoading = true, error = null) ?: UIState(
            data =
                Chart(
                    listChartItems = emptyList(),
                    hourInterval = hourInterval.value,
                    dayInterval = dayInterval.value
                ), isLoading = true, error = null
        )
        _uiState.value = uiState.value.copy(data = uiState.value.data?.copy(chart = chart))
        viewModelScope.launch {
            repository.getChart(coindId, hourInterval.value, dayInterval.value).collect {
                it.fold(onSuccess = {
                    _uiState.value = uiState.value.copy(
                        data = uiState.value.data?.copy(
                            chart = UIState(
                                isLoading = false,
                                data = it,
                                error = null
                            )
                        )
                    )
                }, onFailure = {
                    _uiState.value =
                        uiState.value.copy(
                            data = uiState.value.data?.copy(
                                chart = chart.copy(
                                    error = it,
                                    isLoading = false
                                )
                            )
                        )
                })
            }
        }
    }

    private fun getCoinDetails() {
        viewModelScope.launch {
            combine(
                repository.getCoinDetails(coindId),
                repository.getChart(
                    coindId,
                    HourInterval.THIRTY_MINUTES.value,
                    DayInterval.THIRTY_DAYS.value
                )
            ) { coinDetails, chart ->

                when {
                    //questo lo considerero l errore principale
                    coinDetails.isFailure -> _uiState.value = uiState.value.copy(
                        data = null,
                        isLoading = false,
                        error = coinDetails.exceptionOrNull()
                    )

                    coinDetails.isSuccess && chart.isSuccess -> {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            data = ExpandedCoinDetails(
                                coinDetails.getOrNull(),
                                UIState(isLoading = false, data = chart.getOrNull(), error = null)
                            )
                        )
                    }

                    chart.isFailure && coinDetails.isSuccess -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            data = ExpandedCoinDetails(
                                coinDetails.getOrNull(),
                                UIState(
                                    error = chart.exceptionOrNull(),
                                    isLoading = false,
                                    data = Chart(
                                        listChartItems = emptyList(),
                                        hourInterval = HourInterval.THIRTY_MINUTES.value,
                                        dayInterval = DayInterval.THIRTY_DAYS.value
                                    )
                                )
                            )
                        )

                    }

                }

            }.collect()

        }
    }

    override fun handleEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is CommonEvent.Retry -> getCoinDetails()

            is CoinDetailsEvent.RefreshGraph -> getChartData(
                hourInterval = HourInterval.Companion.safeValueOf(_uiState.value.data?.chart?.data?.hourInterval),
                dayInterval = DayInterval.Companion.safeValueOf(_uiState.value.data?.chart?.data?.dayInterval)
            )

            is CoinDetailsEvent.OnIntervalChange ->
                getChartData(
                    hourInterval = uiEvent.hourInterval,
                    dayInterval = DayInterval.Companion.safeValueOf(
                        uiState.value.data?.chart?.data?.dayInterval
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