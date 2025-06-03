package com.francis.retrofittestdemo.wan_android.data.network

import com.francis.retrofittestdemo.wan_android.data.network.dto.BannersResponseDto
import retrofit2.http.GET

/**
 * WanAndroid接口 获取Banner列表
 */
interface WanAndroidApi {
    /**
     * https://www.wanandroid.com/banner/json
     * @return BannersResponseDto 类型
     */
    @GET("/banner/json")
    suspend fun getBanner(): BannersResponseDto
}