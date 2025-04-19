package com.giuseppe_longhitano.coin.preview.coinlist_preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.giuseppe_longhitano.coin.coin_list.screen.CoinListScreen
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.Id
 import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import java.util.UUID
import kotlin.random.Random.Default.nextDouble


@Preview
@Composable
fun CoinListScreenPreview() {
    CoinListScreen(uiState = UIState<List<Coin>>(data = fakeList())) {  }
}
@Preview
@Composable
fun CoinListScreenLoading() {
    CoinListScreen(uiState = UIState<List<Coin>>(data = fakeList(), isLoading = true)) {  }
}
@Preview
@Composable
fun CoinListScreenPreviewError() {
    CoinListScreen(uiState = UIState<List<Coin>>(data = fakeList(), error = Throwable("Ops.."))) {  }
}
private fun fakeList() =
    (0..10).map {
        Coin(
            id = Id(UUID.randomUUID().toString()),
            name = "Bitcoin",
            symbol = "BTC",
            urlmage = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
            currentPrice = nextDouble()
        )

}