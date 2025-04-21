package com.giuseppe_longhitano.coin.coin_list.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.giuseppe_longhitano.arch.event.NavigationEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.coin.routing.RouteScreen
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.ui.view.widget.base.BaseLazyLoadingList
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import org.koin.androidx.compose.koinViewModel

private const val TAG = "CoinListScreen"

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    coinsListViewModel: CoinListViewModel = koinViewModel(),
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
    uiState: UIState<ListModel<Coin>>,
    handleEvent: (UIEvent) -> Unit
) {
    BaseLazyLoadingList(uiState =uiState, modifier =  modifier, handleEvent = handleEvent, content = {
        CoinItem(modifier = Modifier, coin = it, handleEvent = handleEvent)
    })
}









