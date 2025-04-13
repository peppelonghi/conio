package com.giuseppe_longhitano.repositories

import android.util.Log
import com.giuseppe_longhitano.domain.model.Coin
import com.giuseppe_longhitano.domain.model.Id
import com.giuseppe_longhitano.domain.repositories.CoinRepository
import com.giuseppe_longhitano.network.CoinGeckoService
import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.repositories.utils.toCoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

private const val TAG = "CoinRepositoryImpl"

internal class CoinRepositoryImpl(private val service: CoinGeckoService) : CoinRepository {
    override suspend fun getCoin(): Flow<Result<List<Coin>>> =
        flow {
            emit(Result.success(service.getCoins().map { it.toCoin() }))
        }.catch {
            emit(Result.failure(it))
        }
}


