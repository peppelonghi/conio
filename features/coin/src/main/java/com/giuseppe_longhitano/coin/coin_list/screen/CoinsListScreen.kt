package com.giuseppe_longhitano.coin.coin_list.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.ui.view.widget.base.PageLazyLoadingList
import org.koin.androidx.compose.koinViewModel

private const val TAG = "CoinListScreen"



@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    coinsListViewModel: CoinListViewModel = koinViewModel(),
    onChangeRoute: (Route) -> Unit

) {
    PageLazyLoadingList(
        viewModel = coinsListViewModel,
        modifier = modifier,
        onChangeRoute = onChangeRoute,
        handleEvent = { coinsListViewModel.handleEvent(it) },
        contentItem = { CoinItem(modifier = Modifier, coin = (it as Coin), handleEvent =  coinsListViewModel::handleEvent )})
}











