package com.francis.retrofittestdemo.wan_android.data.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Banner请求返回的数据结构
 */
@JsonClass(generateAdapter = true)
data class BannerDto(
    @Json(name = "desc")
    val desc: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imagePath")
    val imagePath: String,
    @Json(name = "isVisible")
    val isVisible: Int,
    @Json(name = "order")
    val order: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: Int,
    @Json(name = "url")
    val url: String
)
