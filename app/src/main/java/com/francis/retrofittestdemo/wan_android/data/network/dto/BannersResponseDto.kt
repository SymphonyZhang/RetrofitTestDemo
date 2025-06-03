package com.francis.retrofittestdemo.wan_android.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Banner请求返回的数据结构
 */
data class BannersResponseDto(
    @SerializedName("data")
    val data: List<BannerDto>,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
    )