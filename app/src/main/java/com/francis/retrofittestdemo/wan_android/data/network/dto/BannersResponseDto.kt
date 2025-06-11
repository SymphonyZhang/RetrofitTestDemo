package com.francis.retrofittestdemo.wan_android.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Banner请求返回的数据结构
 */
@JsonClass(generateAdapter = true)
data class BannersResponseDto(
    @Json(name = "data")
    val data: List<BannerDto>,
    @Json(name = "errorCode")
    val errorCode: Int,
    @Json(name = "errorMsg")
    val errorMsg: String
    )