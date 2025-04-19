package com.giuseppe_longhitano.network


import com.giuseppe_longhitano.network.utils.CoinGeckoApiKeyInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory



val networkModule = module {

    single { HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    } }
    single {
        CoinGeckoApiKeyInterceptor()
    }
    single {
        OkHttpClient.Builder().build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<CoinGeckoApiKeyInterceptor>())
            .build()
    }

    single {
        val gson: Gson = GsonBuilder().create()
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // Access it directly here
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use Gson converter
            .client(get()) // Inject OkHttpClient
            .build()
    }
     single { get<Retrofit>().create(CoinGeckoService::class.java) }
 }

