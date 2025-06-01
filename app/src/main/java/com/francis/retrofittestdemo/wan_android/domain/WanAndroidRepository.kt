package com.francis.retrofittestdemo.wan_android.domain

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult

/**
 * WanAndroid Repository 仓库接口
 */
interface WanAndroidRepository {
    suspend fun getBanners(): ResponseResult<List<Banner>, NetworkError>
}