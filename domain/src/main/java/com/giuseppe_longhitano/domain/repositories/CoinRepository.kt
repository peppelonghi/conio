package com.giuseppe_longhitano.domain.repositories
import com.giuseppe_longhitano.domain.model.Coin
import kotlinx.coroutines.flow.Flow


interface CoinRepository {
    suspend fun getCoin():Flow<Result<List<Coin>>>
}