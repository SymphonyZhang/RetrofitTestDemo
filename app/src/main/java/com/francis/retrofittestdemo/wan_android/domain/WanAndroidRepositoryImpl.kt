package com.francis.retrofittestdemo.wan_android.domain

import com.francis.retrofittestdemo.core.data.network.RetrofitClient
import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.mappers.toBanner
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

/**
 * WanAndroid Repository 仓库实现 并把请求数据封装成自定义封装类型
 */
class WanAndroidRepositoryImpl : WanAndroidRepository {
    override suspend fun getBanners(): ResponseResult<List<Banner>, NetworkError> {
        return try {
            ResponseResult.Success(RetrofitClient.instance.getBanner().data.map {
                it.toBanner()
            })
        } catch (e: UnresolvedAddressException) {
            ResponseResult.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            ResponseResult.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            ResponseResult.Error(NetworkError.UNKNOWN)
        }
    }
}
