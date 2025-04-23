package com.giuseppe_longhitano.repositories

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.network.CoinGeckoService
import com.giuseppe_longhitano.repositories.utils.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale


internal class CoinRepositoryImpl(private val service: CoinGeckoService, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : CoinRepository {

    override suspend fun getCoin(page: Int): Flow<Result<List<Coin>>> =
        flow {
            emit(Result.success(service.getCoins(page = page).map { it.toDomain() }))
        }.catch {
            emit(Result.failure(it))
        }.flowOn(dispatcher)

    override suspend fun getCoinDetails(id: Id): Flow<Result<CoinDetails>> =
        flow {
            val coinDetails = service.getCoinsDetails(id.value)
            emit(
                Result.success(
                    coinDetails.copy(
                        description = getDescrByLanguege(coinDetails.description)
                    ).toDomain()
                )
            )
        }.catch {
            emit(Result.failure(it))
        }.flowOn(dispatcher)

    override suspend fun getChart(id: Id, interval: String): Flow<Result<Chart>> =
        flow {
            emit(
                Result.success(service.getChartData(id = id.value, days = interval).toDomain().copy(interval = interval))
            )
        }.catch {
            emit(Result.failure(it))
        }.flowOn(dispatcher)


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


