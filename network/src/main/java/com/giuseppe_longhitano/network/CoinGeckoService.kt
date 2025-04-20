package com.giuseppe_longhitano.network

import com.giuseppe_longhitano.network.model.ChartResponseDTO
import com.giuseppe_longhitano.network.model.CoinDTO
import com.giuseppe_longhitano.network.model.CoinDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoService {
    @GET(MARKET)
    suspend fun getCoins(
        @Query(QUERY_PARAM_VS_CURRENCY) currency: String = "eur",
        @Query(QUERY_PARAM_PER_PAGE) perPage: Int = 10,
        @Query(QUERY_PARAM_PAGE) page: Int = 1,
    ): List<CoinDTO>


    @GET(COIN_DETAILS)
    suspend fun getCoinsDetails(
        @Path(QUERY_PARAM_ID) id: String,
    ): CoinDetailsDTO

    @GET(CHARTS)
    suspend fun getChartData(
        @Path(QUERY_PARAM_ID) id: String,
        @Query(QUERY_PARAM_VS_CURRENCY) currency: String = "eur",
        @Query(QUERY_PARAM_DAYS) days: String = "1h",
    ): ChartResponseDTO
}