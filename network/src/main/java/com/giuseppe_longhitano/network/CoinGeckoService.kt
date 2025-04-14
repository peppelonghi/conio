package com.giuseppe_longhitano.network

import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.network.model.CoinDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoService {
    @GET(MARKET)
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "eur",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int =1,
        @Query("price_change_percentage") priceChangePercentage: String? = null,
        @Query("sparkline") sparkline: Boolean? = null
    ): List<CoinDTO>


    @GET(COIN_DETAILS)
    suspend fun getCoinsDetails(
        @Path("id") id: String,
    ): CoinDetailsDTO
}