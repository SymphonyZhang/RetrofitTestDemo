package com.francis.retrofittestdemo.wan_android.data

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.network.BannersRemoteDataSource
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto

/**
 * WanAndroid Repository 通过banner的数据源获取数据
 */
class WanAndroidRepository(
    private val bannersRemoteDataSource: BannersRemoteDataSource = BannersRemoteDataSource()
) {
    suspend fun getBanners(): ResponseResult<List<BannerDto>, NetworkError> = bannersRemoteDataSource.getBanners()
}
