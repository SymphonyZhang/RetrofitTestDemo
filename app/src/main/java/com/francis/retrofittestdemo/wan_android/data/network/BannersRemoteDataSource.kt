package com.francis.retrofittestdemo.wan_android.data.network

import com.francis.retrofittestdemo.core.data.network.networkCall
import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto

/**
 * Banner 数据，网络获取的数据源
 */
class BannersRemoteDataSource(private val wanAndroidApi: WanAndroidApi) {
    /**
     * 这里已经脱壳了，不用BannersResponseDto
     * @return ResponseResult<List<BannerDto>, NetworkError>
     */
    suspend fun getBanners(): ResponseResult<List<BannerDto>, NetworkError> {
        return networkCall({
            wanAndroidApi.getBanner()
        }){
            wanAndroidResponseToResult(it)
        }
    }
}