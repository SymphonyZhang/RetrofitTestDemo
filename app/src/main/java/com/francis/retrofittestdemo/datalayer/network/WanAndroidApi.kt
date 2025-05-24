package com.francis.retrofittestdemo.datalayer.network

import retrofit2.http.GET

interface WanAndroidApi {
    //https://www.wanandroid.com/banner/json
    @GET("/banner/json")
    suspend fun getBanner(): BannerData<List<Banner>>
}