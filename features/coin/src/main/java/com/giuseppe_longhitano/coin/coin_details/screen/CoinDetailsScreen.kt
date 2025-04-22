package com.giuseppe_longhitano.coin.coin_details.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.NavigationEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.coin.coin_details.view.CoinDetailsOtherInfo
import com.giuseppe_longhitano.coin.coin_details.view.CoinDetailsHeader
import com.giuseppe_longhitano.ui.view.atomic_view.ChipGroup
import com.giuseppe_longhitano.ui.view.widget.base.BaseScreen
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.chart.CoinLineChart
import com.giuseppe_longhitano.ui.view.widget.chart.Interval
import com.giuseppe_longhitano.ui.view.widget.drop_down.DropDownEvent
import com.giuseppe_longhitano.ui.view.widget.drop_down.DropDownMenu
import com.giuseppe_longhitano.ui.view.widget.drop_down.ui_model.DropDownModel
import org.koin.androidx.compose.koinViewModel
import com.giuseppe_longhitano.ui.R as RUI

private const val TAG = "CoinListScreen"


@Composable
fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    coinDetailsViewModel: CoinDetailsViewModel = koinViewModel(),
    handleEvent: (NavigationEvent) -> Unit
) {
    val state by coinDetailsViewModel.uiState.collectAsStateWithLifecycle()
    CoinDetailsScreen(
        modifier = modifier,
        state = state,
        handleEvent = { event ->
            when (event) {
                is CoinDetailsEvent  -> coinDetailsViewModel.handleEvent(event)
                is NavigationEvent -> handleEvent.invoke(event)
                else -> throw Throwable("No event found for $event")
            }
        })
}


@Composable
internal fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    state: UIState<ExpandedCoinDetails>,
    handleEvent: (UIEvent) -> Unit
) {
    BaseScreen(
        uiState = state,
        modifier = modifier
            .fillMaxSize(),
        handleEvent = handleEvent
    ) { data ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(RUI.dimen.screen_padding))
        ) {
            CoinDetailsHeader(
                coin = data?.coinDetails?.coin,
                handleEvent = { handleEvent.invoke(it) })

            DropDownMenu(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(RUI.string.interval),
                items = Interval.entries.map { DropDownModel(model = it, value = it.value) },
                handleEvent = {
                    when (it) {
                        is DropDownEvent<*> -> if (it.model is Interval) handleEvent.invoke(
                            CoinDetailsEvent.OnIntervalChange(it.model as Interval)
                        )
                    }
                }
            )
            CoinLineChart(
                state = data?.chart,
                handleEvent = {
                    val event = when (it) {
                        is CommonEvent.Retry -> CoinDetailsEvent.RefreshGraph
                        else -> it
                    }
                    handleEvent.invoke(event)
                },
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            CoinDetailsOtherInfo(coinDetails = data?.coinDetails, handleEvent = handleEvent)
        }
    }
}













