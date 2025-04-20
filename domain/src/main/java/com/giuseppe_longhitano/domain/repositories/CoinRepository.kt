package com.giuseppe_longhitano.domain.repositories

import com.giuseppe_longhitano.domain.model.Chart
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import kotlinx.coroutines.flow.Flow


interface CoinRepository {

    suspend fun getCoin(page: Int = 1): Flow<Result<List<Coin>>>

    suspend fun getCoinDetails(id: Id): Flow<Result<CoinDetails>>

    suspend fun getChart(id: Id, interval: String): Flow<Result<Chart>>

}