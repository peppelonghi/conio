package com.giuseppe_longhitano.coin.preview.coinlist_preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.giuseppe_longhitano.coin.coin_list.screen.CoinListScreen
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import java.util.UUID
import kotlin.random.Random.Default.nextDouble


@Preview
@Composable
fun CoinListScreenPreview() {
    CoinListScreen(uiState = UIState<ListModel<Coin>>(data = ListModel(items = fakeList()))) {  }
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