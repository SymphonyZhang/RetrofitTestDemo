package com.francis.retrofittestdemo.core.domain.util

typealias RootError = Error

/**
 * 返回类型的自定义封装
 */
sealed interface ResponseResult<out D, out E : RootError> {
    // 成功封装
    data class Success<out D>(val data: D) : ResponseResult<D, Nothing>

    // Error封装
    data class Error<out E : RootError>(val error: E) : ResponseResult<Nothing, E>
}

/**
 * 封装内部类型转换
 */
inline fun <D, E : Error, R> ResponseResult<D, E>.map(block: (D) -> R): ResponseResult<R, E> {
    return when (this) {
        is ResponseResult.Error -> this
        is ResponseResult.Success -> ResponseResult.Success(block(data))
    }
}

/**
 * 当返回类型是Success时，执行block
 * 并返回自己，形成链式调用
 */
inline fun <D, E : Error> ResponseResult<D, E>.onSuccess(block: (D) -> Unit): ResponseResult<D, E> {
    return when (this) {
        is ResponseResult.Error -> this
        is ResponseResult.Success -> {
            block(data)
            this
        }
    }
}

/**
 * 当返回类型是Error时，执行block
 * 并返回自己，形成链式调用
 */
inline fun <D, E : Error> ResponseResult<D, E>.onError(block: (E) -> Unit): ResponseResult<D, E> {
    return when (this) {
        is ResponseResult.Error -> {
            block(error)
            this
        }

        is ResponseResult.Success -> this
    }
}