package com.giuseppe_longhitano.network


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory



val networkModule = module {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    single {
        OkHttpClient.Builder().build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
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
    // Provide the API interface implementation
    single { get<Retrofit>().create(CoinGeckoService::class.java) }
 }