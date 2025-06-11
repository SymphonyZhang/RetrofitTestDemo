package com.francis.retrofittestdemo.core.data.network

import com.francis.retrofittestdemo.wan_android.data.network.WanAndroidApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Retrofit 单例
 */
object RetrofitClient {
    private const val BASE_URL = "https://www.wanandroid.com/"

    private val logger by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okhttpClient by lazy { OkHttpClient.Builder().addInterceptor(logger).build() }

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val instance: WanAndroidApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WanAndroidApi::class.java)
    }
}