package com.francis.retrofittestdemo.wan_android.data.network.dto

import com.google.gson.annotations.SerializedName

/**
 * Banner请求返回的数据结构
 */
data class BannerDto(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isVisible")
    val isVisible: Int,
    @SerializedName("order")
    val order: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String
)
