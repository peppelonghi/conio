package com.giuseppe_longhitano.features.coins.coin_details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.ui.ui_model.UIState
import androidx.compose.material3.Text
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

import com.giuseppe_longhitano.arch.UIEvent

 import com.giuseppe_longhitano.features.coins.coin_list.CoinListEvent
import com.giuseppe_longhitano.ui.view.BaseScreen
import androidx.compose.runtime.getValue
import com.giuseppe_longhitano.domain.model.CoinDetails

private const val TAG = "CoinListScreen"


@Composable
fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    coinDetailsViewModel: CoinDetailsViewModel = koinViewModel(),
    handleEvent: (UIEvent) -> Unit
) {
    val state by coinDetailsViewModel.uiState.collectAsStateWithLifecycle()
    CoinDetailsScreen(modifier =modifier,state = state, handleEvent = {})
}


@Composable
private fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    state: UIState<CoinDetails>,
    handleEvent: (CoinListEvent) -> Unit
) {
    BaseScreen(uiState = state, modifier = modifier) {
        Text(state.data.toString())
    }
}