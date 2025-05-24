package com.francis.retrofittestdemo.datalayer.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.wanandroid.com/"

    private val logger by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okhttpClient by lazy { OkHttpClient.Builder().addInterceptor(logger).build() }

    val instance: WanAndroidApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WanAndroidApi::class.java)
    }
}