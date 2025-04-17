package com.giuseppe_longhitano.coin.coin_list.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.ui.ui_model.UIState
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.NavigationEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.routing.RouteScreen
import com.giuseppe_longhitano.ui.R

import com.giuseppe_longhitano.ui.view.widget.base.BaseScreen

private const val TAG = "CoinListScreen"

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    coinsListViewModel: CoinsListViewModel = koinViewModel(),
    handleEvent: (NavigationEvent) -> Unit
) {
    val state by coinsListViewModel.uiState.collectAsStateWithLifecycle()
    CoinListScreen(modifier, state, handleEvent = {
        when (it) {
            is CoinListEvent.ClickedCoin -> handleEvent.invoke(
                NavigationEvent(
                    RouteScreen.CoinDetailScreen(
                        it.id.value
                    )
                )
            )
            else -> coinsListViewModel.handleEvent(it)
        }
    }
    )

}


@Composable
internal fun CoinListScreen(
    modifier: Modifier = Modifier,
    uiState: UIState<List<Coin>>,
    handleEvent: (UIEvent) -> Unit
) {
    BaseScreen(uiState =uiState,modifier = modifier, handleEvent = handleEvent) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = dimensionResource( R.dimen.screen_padding))
        ) {
            items(
                items = uiState.data as List<Coin>,
                key = { item -> item.id.value }
            ) { item ->
                CoinItem(modifier = Modifier, coin = item, handleEvent = handleEvent)
            }
        }
    }

}
