package com.giuseppe_longhitano.baseproject.coin_details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.ui.ui_model.UIState
import androidx.compose.material3.Text
import org.koin.androidx.compose.koinViewModel

import com.giuseppe_longhitano.arch.UIEvent

import com.giuseppe_longhitano.baseproject.coin_list.CoinListEvent
import com.giuseppe_longhitano.ui.view.BaseScreen

private const val TAG = "CoinListScreen"


@Composable
fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    coinViewModel: CoindDetailsViewModel = koinViewModel(),
    handleEvent: (UIEvent) -> Unit
) {
    Text("Details")
}


@Composable
private fun CoinDetailsScreen(
    modifier: Modifier = Modifier,
    state: UIState<List<Coin>>,
    handleEvent: (CoinListEvent) -> Unit
) {
    BaseScreen(state, modifier) {
        Text("Details")
    }

}