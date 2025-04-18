package com.giuseppe_longhitano.coin.viewmodel_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.coin.coin_list.screen.CoinsListViewModel
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import io.mockk.coEvery
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

@OptIn(ExperimentalCoroutinesApi::class)
class CoinListViewModelTest {

    private val repository = mockk<CoinRepository>(relaxed = true)

    private val sut by lazy { CoinsListViewModel(repository) }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test success`() = runTest {
        coEvery { repository.getCoin() } returns flowOf(Result.success(emptyList()))
        sut.uiState.test {
            assert(awaitItem().data?.isEmpty() == true)

        }
    }

    @Test
    fun `test failure`() = runTest {
        coEvery { repository.getCoin() } returns flowOf(Result.failure(Throwable()))
        sut.uiState.test {
            assert(awaitItem().error != null)
        }
    }

    @Test
    fun `test event`() = runTest {
        coEvery { repository.getCoin() } returns flowOf(Result.failure(Throwable()))
        sut.uiState.test {
            assert(awaitItem().error != null)
        }
        sut.handleEvent(CommonEvent.Retry)
    }


}