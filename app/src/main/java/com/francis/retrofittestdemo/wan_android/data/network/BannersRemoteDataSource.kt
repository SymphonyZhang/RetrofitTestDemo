package com.francis.retrofittestdemo.wan_android.data.network

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

/**
 * Banner 数据，网络获取的数据源
 */
class BannersRemoteDataSource(private val wanAndroidApi: WanAndroidApi) {
    suspend fun getBanners(): ResponseResult<List<BannerDto>, NetworkError> {
        return try {
            ResponseResult.Success(wanAndroidApi.getBanner().data)
        } catch (e:HttpException){
            when(e.code()){
                408 -> ResponseResult.Error(NetworkError.REQUEST_TIMEOUT)
                429 -> ResponseResult.Error(NetworkError.TOO_MANY_REQUESTS)
                in 500..599 -> ResponseResult.Error(NetworkError.SERVER_ERROR)
                else -> ResponseResult.Error(NetworkError.UNKNOWN)
            }
        }
        catch (e: UnresolvedAddressException) {
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