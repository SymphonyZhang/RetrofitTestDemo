package com.francis.retrofittestdemo.core.data.network

import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T,reified R> networkCall(execute: () -> T,handleResponse:(T)->ResponseResult<R, NetworkError>): ResponseResult<R, NetworkError> {
    val response =  try {
        execute()
    } catch (e: HttpException) {
        return when (e.code()) {
            408 -> ResponseResult.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> ResponseResult.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> ResponseResult.Error(NetworkError.SERVER_ERROR)
            else -> ResponseResult.Error(NetworkError.UNKNOWN)
        }
    } catch (e: UnresolvedAddressException) {
        return ResponseResult.Error(NetworkError.SERVER_ERROR)
    } catch (e: UnknownHostException) {
        return ResponseResult.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return ResponseResult.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return ResponseResult.Error(NetworkError.UNKNOWN)
    }
    return handleResponse(response)
}