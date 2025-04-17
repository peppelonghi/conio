package com.giuseppe_longhitano.features.preview.coinlist_preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.features.coins.coin_list.CoinItem

@Preview
@Composable
fun CoinItemPreview() {
    CoinItem(
        coin = Coin(
            id = Id("bitcoin"),
            name = "Bitcoin",
            symbol = "BTC",
            urlmage = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
            currentPrice = "30000.00".toDouble()
        )
    ) { }
}
