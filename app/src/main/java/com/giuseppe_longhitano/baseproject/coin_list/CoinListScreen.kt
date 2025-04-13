package com.giuseppe_longhitano.baseproject.coin_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.ui.ui_model.UIState
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.NavigationEvent
import com.giuseppe_longhitano.baseproject.routing.RouteScreen

import com.giuseppe_longhitano.ui.view.BaseScreen

private const val TAG = "CoinListScreen"

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    coinViewModel: CoinViewModel = koinViewModel(),
    handleEvent: (NavigationEvent) -> Unit
) {
    val state by coinViewModel.uiState.collectAsStateWithLifecycle()
    CoinListScreen(modifier, state, handleEvent = {
        when (it) {
            is CoinListEvent.ClickedCoin -> handleEvent.invoke(NavigationEvent(RouteScreen.CoinDetailScreen(it.id.value)))
        }
    }
    )

}


@Composable
private fun CoinListScreen(
    modifier: Modifier = Modifier,
    state: UIState<List<Coin>>,
    handleEvent: (CoinListEvent) -> Unit
) {
    BaseScreen(state, modifier) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = state.data as List<Coin>,
                key = { item -> item.id.value } // Provide a key
            ) { item ->
                CoinItem(modifier = Modifier.fillMaxSize(), coin = item, handleEvent = handleEvent)
            }
        }
    }

}