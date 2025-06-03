package com.francis.retrofittestdemo.wan_android.data.network

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannersResponseDto

/**
 * wanandroid 返回结果根据 errorCode 进行封装
 * @return ResponseResult<List<BannerDto>, NetworkError>
 */
fun  wanAndroidResponseToResult(response: BannersResponseDto): ResponseResult<List<BannerDto>, NetworkError> {
    return when(response.errorCode){
        0 -> ResponseResult.Success(data = response.data)
        -1001 -> ResponseResult.Error(NetworkError.LOGIN_EXPIRED)
        else -> ResponseResult.Error(NetworkError.UNKNOWN)
    }
}