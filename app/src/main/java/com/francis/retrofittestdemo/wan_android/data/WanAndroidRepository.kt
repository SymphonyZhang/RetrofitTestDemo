package com.francis.retrofittestdemo.wan_android.data

import com.francis.retrofittestdemo.core.data.network.RetrofitClient
import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.core.domain.util.map
import com.francis.retrofittestdemo.wan_android.data.mappers.toBanner
import com.francis.retrofittestdemo.wan_android.data.network.BannersRemoteDataSource
import com.francis.retrofittestdemo.wan_android.domain.Banner

/**
 * WanAndroid Repository 通过banner的数据源获取数据
 */
class WanAndroidRepository(
    private val bannersRemoteDataSource: BannersRemoteDataSource = BannersRemoteDataSource(RetrofitClient.instance)
) {
    /**
     * 从 BannerDto 转为 Banner
     * @return ResponseResult<List<Banner>, NetworkError>
     */
    suspend fun getBanners(): ResponseResult<List<Banner>, NetworkError> =
        bannersRemoteDataSource
            .getBanners()
            .map {
                it.map { bannerDto ->
                    bannerDto.toBanner()
                }
            }
}
