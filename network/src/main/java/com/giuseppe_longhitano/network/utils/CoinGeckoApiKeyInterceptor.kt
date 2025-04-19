package com.giuseppe_longhitano.network.utils

import com.giuseppe_longhitano.network.API_KEY_QUERY
import com.giuseppe_longhitano.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CoinGeckoApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain):  Response {
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter(API_KEY_QUERY, BuildConfig.API_KEY)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}