package com.giuseppe_longhitano.domain.repositories
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.CoinDetails
import com.giuseppe_longhitano.domain.model.Id
import kotlinx.coroutines.flow.Flow


interface CoinRepository {
    suspend fun getCoin():Flow<Result<List<Coin>>>
    suspend fun getCoinDetails(id: Id):Flow<Result<CoinDetails>>
}