package com.giuseppe_longhitano.network.utils

import com.giuseppe_longhitano.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


//class HeaderInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain):  Response {
//        val request = chain.request().newBuilder()
//            .addHeader("header", BuildConfig.API_KEY)
//            // Add more headers as needed
//            .build()
//        return chain.proceed(request)
//    }
//}