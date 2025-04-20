package com.giuseppe_longhitano.coin.preview.mock_model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.ChartItem
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlin.random.Random

val mockCoin = Coin(
    id = Id("bitcoin"),
    name = "Bitcoin",
    symbol = "BTC",
    urlmage = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
    currentPrice = "30000.00".toDouble()
)

val coinDetails = ExpandedCoinDetails(
    chart =   UIState<Chart>(data = createFakeChart()),
    coinDetails = CoinDetails(
        description = LoremIpsum(10).values.first(),
        coin = Coin(
            id = Id("bitcoin"),
            name = "Bitcoin",
            symbol = "BTC",
            urlmage = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
            currentPrice = "30000.00".toDouble()
        )
    )
)
private fun createFakeChartItem(): ChartItem {
    val title = "Chart Item ${Random.nextInt(1, 100)}"
    val numDataPoints = Random.nextInt(20, 50)
    val basePrice = Random.nextDouble(100.0, 1000.0)
    val priceFluctuation = 0.05

    val item = mutableListOf<List<Double>>()
    var currentPrice = basePrice

    for (i in 0 until numDataPoints) {
        val priceChange = Random.nextDouble(-priceFluctuation, priceFluctuation)
        currentPrice += currentPrice * priceChange
        currentPrice = maxOf(0.0, currentPrice)
        item.add(listOf(i.toDouble(), currentPrice))
    }

    return ChartItem(title, item)
}

private fun createFakeChart(): Chart {
    val numChartItems = Random.nextInt(1, 4)
    val listChartItems = (0 until numChartItems).map { createFakeChartItem() }

    return Chart(
        itemsChart = listChartItems,
        dayInterval = "1 Day",
        hourInterval = "1 Hour"
    )
}