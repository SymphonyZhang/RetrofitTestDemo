package com.francis.retrofittestdemo.wan_android.data.mappers

import com.francis.retrofittestdemo.wan_android.data.network.dto.BannerDto
import com.francis.retrofittestdemo.wan_android.domain.Banner

/**
 * 扩展函数，将BannerDto转换为Banner 从data层转为domain层的类型
 */
fun BannerDto.toBanner(): Banner {
    return Banner(
        desc = desc,
        id = id,
        imagePath = imagePath,
        isVisible = isVisible,
        order = order,
        title = title,
        type = type,
        url = url
    )
}