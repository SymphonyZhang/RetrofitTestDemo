package com.francis.retrofittestdemo.wan_android.presentation.banner_list

import com.francis.retrofittestdemo.core.presentation.uitl.UiText

/**
 * 事件的封装
 */
sealed interface BannerEvent {
    data class BannerError(val error: UiText) : BannerEvent
}