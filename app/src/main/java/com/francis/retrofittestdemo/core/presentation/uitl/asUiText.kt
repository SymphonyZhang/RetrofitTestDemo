package com.francis.retrofittestdemo.core.presentation.uitl

import com.francis.retrofittestdemo.R
import com.francis.retrofittestdemo.core.domain.util.NetworkError
import com.francis.retrofittestdemo.core.domain.util.ResponseResult

/**
 * 把NetworkError中的错误类型跟UI显示字符串进行配对转换,转成UiText类型
 */
fun NetworkError.asUiText(): UiText {
    return when (this) {
        NetworkError.REQUEST_TIMEOUT -> UiText.StringResource(R.string.request_timeout)
        NetworkError.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
        NetworkError.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        NetworkError.SERVER_ERROR -> UiText.StringResource(R.string.error_unknown)
        NetworkError.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
        NetworkError.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
    }
}

/**
 * 把ResponseResult中的错误类型跟UI显示字符串进行配对转换,转成UiText类型
 */
fun ResponseResult.Error<NetworkError>.asErrorUiText() = error.asUiText()