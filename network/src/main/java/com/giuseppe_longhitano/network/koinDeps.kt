package com.giuseppe_longhitano.network


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
 val networkModule = module {
    // Provide OkHttpClient
    single {
        OkHttpClient.Builder().build()
    }


//    // Provide Retrofit
     single {
         val contentType = "application/json".toMediaType()
         val json = Json {
             ignoreUnknownKeys = true
             coerceInputValues = true
         }
        Retrofit.Builder()
            .baseUrl("https://api.production.com/") // Use the BASE_URL from BuildConfig
            .addConverterFactory(json.asConverterFactory(contentType)) // Use Kotlin Serialization converter
            .client(get())
            .build()
    }

}