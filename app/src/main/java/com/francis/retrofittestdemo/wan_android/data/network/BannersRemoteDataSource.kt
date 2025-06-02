package com.francis.retrofittestdemo.wan_android.data.network

import com.francis.retrofittestdemo.core.data.network.RetrofitClient
import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

/**
 * Banner 数据，网络获取的数据源
 */
class BannersRemoteDataSource {
    suspend fun getBanners(): ResponseResult<List<BannerDto>, NetworkError> {
        return try {
            ResponseResult.Success(RetrofitClient.instance.getBanner().data)
        } catch (e: UnresolvedAddressException) {
            ResponseResult.Error(NetworkError.SERVER_ERROR)
        } catch (e: UnknownHostException) {
            ResponseResult.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            ResponseResult.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            ResponseResult.Error(NetworkError.UNKNOWN)
        }
    }
}