package com.giuseppe_longhitano.coin.coin_details.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.coin.coin_details.view.CoinDetailsHeader
import com.giuseppe_longhitano.coin.coin_details.view.CoinDetailsOtherInfo
import com.giuseppe_longhitano.ui.view.widget.chips.ChipGroup
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.chart.Interval

import com.giuseppe_longhitano.ui.view.shared.common_ui_model.SelectableItem
import com.giuseppe_longhitano.ui.view.widget.base.PageScreen
import com.giuseppe_longhitano.ui.view.widget.chart.CoinLineChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import com.giuseppe_longhitano.ui.R as RUI

private const val TAG = "CoinListScreen"


@Composable
fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    coinDetailsViewModel: CoinDetailsViewModel = koinViewModel(),
    onNavigationEvent: (Route) -> Unit
) {

    PageScreen(
        onChangeRoute = onNavigationEvent,
        viewModel = coinDetailsViewModel,
        modifier = modifier,
        contentScreen = {
            CoinDetailsContent(
                modifier = modifier,
                uiState = it,
                handleEvent =  coinDetailsViewModel::handleEvent)
        },
        handleEvent =  coinDetailsViewModel::handleEvent,
    )


}


@Composable
private fun CoinDetailsContent(
    modifier: Modifier = Modifier,
    uiState: UIState<ExpandedCoinDetails>,
    handleEvent: (UIEvent) -> Unit
) {
    val data = uiState.data
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(RUI.dimen.screen_padding))
    ) {
        CoinDetailsHeader(
            coin = uiState.data?.coinDetails?.coin,
            handleEvent = { handleEvent.invoke(it) })

        ChipGroup(
            items = Interval.entries.map { SelectableItem(model = it, label = it.value) },
            selectedItem = Interval.entries.first().value,
            handleEvent = { value ->
                handleEvent.invoke(
                    CoinDetailsEvent.OnIntervalChange(value.model)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(RUI.string.interval),
        )

        CoinLineChart(
            state = data?.chartUIState,
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
        CoinDetailsOtherInfo(
            coinDetails = data?.coinDetails, handleEvent = handleEvent, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}










