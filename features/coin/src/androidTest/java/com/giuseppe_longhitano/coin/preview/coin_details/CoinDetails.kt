package com.giuseppe_longhitano.coin.preview.coin_details

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.giuseppe_longhitano.coin.coin_details.screen.CoinDetailsScreen
import com.giuseppe_longhitano.coin.coin_details.screen.ui_model.ExpandedCoinDetails
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.ChartItem
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id

import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import kotlin.random.Random

@Preview
@Composable
fun CoinDetailsPreviewLoading() {
    Surface {
        CoinDetailsScreen(state = UIState<ExpandedCoinDetails>(isLoading = true)) {}
    }
}

@Preview
@Composable
fun CoinDetailsPreviewSuccess() {
    Surface {
        CoinDetailsScreen(state = UIState<ExpandedCoinDetails>(isLoading = false, data = coinDetails)){}
    }
}

@Preview
@Composable
fun CoinDetailsPreviewError() {
    Surface {
        CoinDetailsScreen(state = UIState<ExpandedCoinDetails>(isLoading = false,error = Throwable("Ops.."))) {}
    }
}
@Preview
@Composable
fun CoinDetailsPreviewSuccessWhitChartError() {
    Surface {
        val chartError = UIState<Chart>(error = Throwable("Ops.."), isLoading = false)
        CoinDetailsScreen(state = UIState<ExpandedCoinDetails>(isLoading = false, data = coinDetails.copy(chart = chartError))) {}
     }
}



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
    val numDataPoints = Random.nextInt(20, 50) // Random number of data points between 20 and 50
    val basePrice = Random.nextDouble(100.0, 1000.0) // Starting price somewhere between 100 and 1000
    val priceFluctuation = 0.05 // Maximum price change per step (5%)

    val item = mutableListOf<List<Double>>()
    var currentPrice = basePrice

    for (i in 0 until numDataPoints) {
        val priceChange = Random.nextDouble(-priceFluctuation, priceFluctuation)
        currentPrice += currentPrice * priceChange // Apply the change to the current price
        currentPrice = maxOf(0.0, currentPrice) // Ensure the price doesn't go negative
        item.add(listOf(i.toDouble(), currentPrice)) // Add the timestamp/index and price
    }

    return ChartItem(title, item)
}

private fun createFakeChart(): Chart {
    val numChartItems = Random.nextInt(1, 4) // Random number of chart items
    val listChartItems = (0 until numChartItems).map { createFakeChartItem() }

    return Chart(
        itemsChart = listChartItems,

    )
}
