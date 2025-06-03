package com.francis.retrofittestdemo.wan_android.data.network

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannersResponseDto

fun  wanAndroidResponseToResult(response: BannersResponseDto): ResponseResult<List<BannerDto>, NetworkError> {
    return when(response.errorCode){
        0 -> ResponseResult.Success(data = response.data)
        -1001 -> ResponseResult.Error(NetworkError.LOGIN_EXPIRED)
        else -> ResponseResult.Error(NetworkError.UNKNOWN)
    }
}