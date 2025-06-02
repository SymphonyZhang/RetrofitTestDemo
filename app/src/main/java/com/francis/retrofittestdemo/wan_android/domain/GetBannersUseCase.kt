package com.francis.retrofittestdemo.wan_android.domain

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.core.domain.util.map
import com.francis.retrofittestdemo.wan_android.data.WanAndroidRepository
import com.francis.retrofittestdemo.wan_android.data.mappers.toBanner

/**
 * domain层UseCase 通过 data层的 Repository 获取数据
 */
class GetBannersUseCase(private val repository: WanAndroidRepository = WanAndroidRepository()) {
    suspend fun getBanners(): ResponseResult<List<Banner>, NetworkError> =
        repository.getBanners().map { it.map { bannerDto -> bannerDto.toBanner() } }
}