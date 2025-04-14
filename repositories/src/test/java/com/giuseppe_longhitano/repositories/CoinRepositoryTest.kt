package com.giuseppe_longhitano.repositories

import app.cash.turbine.test
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.network.CoinGeckoService
import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.network.model.CoinDetailsDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class CoinRepositoryTest {

    private val service = mockk<CoinGeckoService>(relaxed = true)
    private val sut  = CoinRepositoryImpl(service)


    @Test
    fun `test_success coins`() = runTest  {
        coEvery { service.getCoins() } returns listOf<CoinDTO>()
        sut.getCoin().test {
            // assert only success
            assert(awaitItem().isSuccess)
            awaitComplete()
        }
    }
    @Test
    fun `test_failure coins`() = runTest  {
        coEvery { service.getCoins() } throws Throwable()
        sut.getCoin().test {
            // assert only failure
            assert(awaitItem().isFailure)
            awaitComplete()
        }
    }


    @Test
    fun `test_success details coin`() = runTest  {
        coEvery { service.getCoinsDetails(any()) } returns CoinDetailsDTO(id = "bitCoin", symbol = "BTC", name = "Bitcoin", description = mapOf())
        sut.getCoinDetails(Id("bitCoin")).test {
            // assert only success
            val item = awaitItem()
            assert(item.isSuccess)
            awaitComplete()
        }
    }
    @Test
    fun `test_failure details coin`() = runTest  {
        coEvery { service.getCoinsDetails(any()) }  throws Throwable()
        sut.getCoinDetails(Id("bitCoin")).test {
            // assert only failure
            assert(awaitItem().isFailure)
            awaitComplete()
        }
    }
}