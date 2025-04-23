package com.giuseppe_longhitano.repositories

import app.cash.turbine.test
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.network.CoinGeckoService
import com.giuseppe_longhitano.network.model.ChartResponseDTO
import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.network.model.CoinDetailsDTO
import com.giuseppe_longhitano.network.model.ImagesDTO
import com.giuseppe_longhitano.network.model.LinksDTO
import com.giuseppe_longhitano.network.model.MarketDataDTO
import com.giuseppe_longhitano.network.model.ReposUrlDTO
import com.giuseppe_longhitano.repositories.utils.EUR
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class CoinRepositoryTest {

    private val service = mockk<CoinGeckoService>(relaxed = true)
    private val sut = CoinRepositoryImpl(service)


    @Test
    fun `test_success coins`() = runTest {
        coEvery { service.getCoins() } returns listOf()
        sut.getCoin().test {
            // assert only success
            assert(awaitItem().isSuccess)
            awaitComplete()
        }
    }

    @Test
    fun `test_failure coins`() = runTest {
        coEvery { service.getCoins() } throws Throwable()
        sut.getCoin().test {
            // assert only failure
            assert(awaitItem().isFailure)
            awaitComplete()
        }
    }


    @Test
    fun `test_success details coin`() = runTest {
        coEvery { service.getCoinsDetails(any()) } returns CoinDetailsDTO(
            id = "bitCoin",
            symbol = "BTC",
            name = "Bitcoin",
            description = mapOf("en" to "description"),
            marketDataDTO = MarketDataDTO(emptyMap()),
            image = ImagesDTO(small = "", large = "", thumb = ""),
            links = LinksDTO(
                homepage = emptyList(),
                whitepaper = "",
                blockchainSite = emptyList(),
                officialForumUrl = emptyList(),
                chatUrl = emptyList(),
                announcementUrl = emptyList(),
                reposUrl = ReposUrlDTO(emptyList(), emptyList()),
                snapshotUrl = "",
                twitterScreenName = "",
                facebookUsername = "",
                bitcointalkThreadIdentifier = "",
                telegramChannelIdentifier = "",
                subredditUrl = ""
        )
        )
        sut.getCoinDetails(Id("bitCoin")).test {
            // assert only success
            val item = awaitItem()
            assert(item.isSuccess)
            awaitComplete()
        }
    }

    @Test
    fun `test_failure details coin`() = runTest {
        coEvery { service.getCoinsDetails(any()) } throws Throwable()
        sut.getCoinDetails(Id("bitCoin")).test {
            // assert only failure
            assert(awaitItem().isFailure)
            awaitComplete()
        }
    }
    @Test
    fun `test_error chart`() = runTest {
        coEvery { service.getChartData(any(), any(), any()) } throws Throwable()
        sut.getChart(Id("bitCoin"), "1h").test {
            // assert only success
            assert(awaitItem().isFailure)
            awaitComplete()
        }

    }

    @Test
    fun `test_success chart`() = runTest {
        coEvery { service.getChartData(any(), any(), any()) } returns
            ChartResponseDTO(emptyList(), emptyList(), emptyList())

        sut.getChart(Id("bitCoin"), "1h").test {
            // assert only success
            assert(awaitItem().isSuccess)
            awaitComplete()
        }

    }
}