package com.giuseppe_longhitano.coin.coin_details.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.ui.ui_model.UIState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.widget.base.BaseScreen
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.ui.view.widget.chart.CoinLineChart
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.coin.coin_details.view.CoinItemInfo
import com.giuseppe_longhitano.coin.utils.DayInterval
import com.giuseppe_longhitano.coin.utils.HourInterval
import com.giuseppe_longhitano.features.coin.R
import com.giuseppe_longhitano.ui.view.widget.LabelValueItem
import com.giuseppe_longhitano.ui.view.widget.chart.ChartEvent
import com.giuseppe_longhitano.ui.R as RUI

private const val TAG = "CoinListScreen"


@Composable
fun CoinDetailsScreen(
    coinDetailsViewModel: CoinDetailsViewModel = koinViewModel(),
    handleEvent: (UIEvent) -> Unit
) {
    val state by coinDetailsViewModel.uiState.collectAsStateWithLifecycle()
    CoinDetailsScreen(
        state = state,
        handleEvent = { event ->
            coinDetailsViewModel.handleEvent(event)
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CoinDetailsScreen(
    state: UIState<ExpandedCoinDetails>,
    handleEvent: (UIEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    BaseScreen(
        uiState = state,
        modifier = Modifier
            .fillMaxSize(),
        handleEvent = handleEvent
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(dimensionResource(RUI.dimen.screen_padding))
        ) {
            CoinItemInfo(coin = state.data?.coinDetails?.coin) { }
            CoinLineChart(
                hourIntervals = HourInterval.entries.map { it.value },
                dayIntervals = DayInterval.entries.map { it.value },
                state = state.data?.chart,
                handleEvent = {
                    val event = when (it) {
                        is CommonEvent.Retry -> CoinDetailsEvent.RefreshGraph
                        is ChartEvent.OnIntervalChange -> CoinDetailsEvent.OnIntervalChange(
                            HourInterval.safeValueOf(it.hourInterval)
                        )

                        is ChartEvent.OnDaysChange -> CoinDetailsEvent.OnIntervalChange(
                            HourInterval.safeValueOf(
                                it.dayInterval
                            )
                        )

                        else -> it
                    }
                    handleEvent.invoke(event)
                },
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            CoinDetailsInfo(coinDetails = state.data?.coinDetails)
        }


    }
}

@Composable
fun CoinDetailsInfo(coinDetails: CoinDetails?) {
    coinDetails?.let {
        Spacer(modifier = Modifier.padding(8.dp))
        Card {
            LabelValueItem(
                modifier = Modifier.padding(16.dp),
                label = stringResource(R.string.description),
                value = coinDetails.description

            )
        }
    } ?: Text(text = stringResource(R.string.no_info), style = MaterialTheme.typography.titleMedium)
}











