package com.francis.retrofittestdemo.wan_android.presentation.models

import com.francis.retrofittestdemo.wan_android.domain.Banner

/**
 * Banner UI层数据结构
 */
data class BannerUi(
    val id: Int,
    val imagePath: String,
    val title: String,
)

/**
 * 把domain层的Banner转为UI层BannerUi
 */
fun Banner.toBannerUi(): BannerUi {
    return BannerUi(
        id = id,
        imagePath = imagePath,
        title = title
    )
}