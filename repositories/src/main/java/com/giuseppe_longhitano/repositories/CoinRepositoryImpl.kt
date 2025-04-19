package com.giuseppe_longhitano.repositories

import android.util.Log
import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.network.CoinGeckoService
import com.giuseppe_longhitano.repositories.utils.toChart
import com.giuseppe_longhitano.repositories.utils.toCoin
import com.giuseppe_longhitano.repositories.utils.toCoinDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.Locale
import kotlin.text.orEmpty

private const val TAG = "CoinRepositoryImpl"

internal class CoinRepositoryImpl(private val service: CoinGeckoService) : CoinRepository {


    override suspend fun getCoin(page: Int): Flow<Result<List<Coin>>> =
        flow {
            emit(Result.success(service.getCoins(page = page).map { it.toCoin() }))
        }.catch {
            emit(Result.failure(it))
        }

    override suspend fun getCoinDetails(id: Id): Flow<Result<CoinDetails>> =
        flow {
            val coinDetails = service.getCoinsDetails(id.value)
            emit(
                Result.success(
                    coinDetails.copy(
                        description = getDescrByLanguege(coinDetails.description)
                    ).toCoinDetails()
                )
            )
        }.catch {
            emit(Result.failure(it))
        }

    override suspend fun getChart(id: Id , interval: String): Flow<Result<Chart>> =
        flow {

            val result = service.getChartData(id = id.value, days =  interval)
            emit(
                Result.success(result.toChart().copy(interval = interval))
            )
        }.catch {
             emit(Result.failure(it))
        }


    //si protrebbe localizzare un pó tutto
    private fun getDescrByLanguege(
        description: Map<String, String>,
        fallbackLanguage: String = Locale.UK.language
    ): Map<String, String> {
        val locale = Locale.getDefault().language
        val desc = description[locale].orEmpty()
        return if (desc.isEmpty()) mapOf(fallbackLanguage to description[fallbackLanguage].orEmpty()) else mapOf(
            locale to desc
        )
    }

}


