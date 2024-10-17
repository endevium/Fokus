package com.example.fokus.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request

object RetrofitClient {
    // API URL
    private const val BASE_URL = "https://scorpion-workable-panda.ngrok-free.app/"

    private var authToken: String? = null

    fun setToken(token: String) {
        authToken = token
    }

    // Build http client with interceptor
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()

                authToken?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }

                chain.proceed(requestBuilder.build())
            }.build()
    }

    // Build retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}