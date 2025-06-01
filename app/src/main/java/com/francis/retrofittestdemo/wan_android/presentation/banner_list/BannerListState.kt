package com.francis.retrofittestdemo.wan_android.presentation.banner_list

import androidx.compose.runtime.Immutable
import com.francis.retrofittestdemo.wan_android.presentation.models.BannerUi

/**
 * Banner UI层数据封装
 */
@Immutable
data class BannerListState(
    val isLoading: Boolean = false,
    val banners: List<BannerUi> = emptyList()
)
