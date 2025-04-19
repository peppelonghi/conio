package com.giuseppe_longhitano.coin.viewmodel_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.coin.coin_details.screen.CoinDetailsEvent
import com.giuseppe_longhitano.coin.coin_details.screen.CoinDetailsViewModel
import com.giuseppe_longhitano.ui.view.widget.chart.Interval
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.ChartItem
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailsViewModelTest {

    private val repository = mockk<CoinRepository>(relaxed = true)

    private val mockCoinId = "bitcoin"

    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true) {
        every { get<String>("id") } returns mockCoinId
    }
    private val mockCoin = CoinDetails(
        coin = Coin(
            id = Id("bitcoin"),
            name = "Bitcoin",
            symbol = "BTC",
            urlmage = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
            currentPrice = "30000.00".toDouble()
        ), description = LoremIpsum().values.toString()
    )

    private val mockChart = createFakeChart()

    private val sut by lazy {
        CoinDetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,

        )
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `test all success`() = runTest {
        coEvery { repository.getCoinDetails(any()) } returns flowOf(Result.success(mockCoin))
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.success(mockChart))
        sut.uiState.test {
            val item = awaitItem()
            assert(item.data != null)
            assert(item.data?.chart?.data != null)
            assert(item.data?.coinDetails != null)
            assert(item.error == null)
            assert(item.data?.chart?.error == null)
        }
    }

    @Test
    fun `test main error with chart success`() = runTest {
        coEvery { repository.getCoinDetails(any()) } returns flowOf(Result.failure(Throwable()))
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.success(mockChart))
        sut.uiState.test {
            val item = awaitItem()
            assert(item.data == null)
            assert(item.error != null)
        }
    }

    @Test
    fun `test main success with chart error`() = runTest {
        coEvery { repository.getCoinDetails(any()) } returns flowOf(Result.success(mockCoin))
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.failure(Throwable()))
        sut.uiState.test {
            val item = awaitItem()
            assert(item.data != null)
            assert(item.error == null)
            assert(item.data?.chart?.error != null)
            println(item.data?.chart)
        }
    }

    @Test
    fun `test getChart success`() = runTest {
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.success(mockChart))
        coEvery { repository.getCoinDetails(any()) } returns flowOf(Result.success(mockCoin))
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.success(mockChart))
        sut.uiState.test {
            val item = awaitItem()
            assert(item.data?.chart?.data == mockChart)
        }
        sut.handleEvent(CommonEvent.Retry)
        sut.handleEvent(CoinDetailsEvent.RefreshGraph)
    }

    @Test
    fun `test chart error`() = runTest {
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.success(mockChart))
        coEvery { repository.getCoinDetails(any()) } returns flowOf(Result.success(mockCoin))
        coEvery {
            repository.getChart(
                any(),
                any(),

            )
        } returns flowOf(Result.failure(Throwable()))
        sut.uiState.test {
            val item = awaitItem()
            assert(item.data?.chart?.error !=null)
        }
        sut.handleEvent(CommonEvent.Retry)
        sut.handleEvent(CoinDetailsEvent.RefreshGraph)
    }

    @Test
    //VERIFICO CHE DATO UN EVENTO VENGA CHIAMATA LA GIUSTA SERIE DI FUNZIONALITA'
    fun `test events`() = runTest {
        sut.handleEvent(CommonEvent.Retry)
        coVerify(exactly = 1) { repository.getCoinDetails(any()) }
        coVerify(exactly = 1) { repository.getChart(any(), any()  ) }
        sut.handleEvent(CoinDetailsEvent.RefreshGraph)
        coVerify(exactly = 2) { repository.getChart(any(), any()  ) }
        sut.handleEvent(CoinDetailsEvent.OnIntervalChange(Interval.THREE_MONTHS))
         coVerify(exactly =3) { repository.getChart(any(), any()) }
    }


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
            listChartItems = listChartItems,
            interval = "1",

        )
    }
}