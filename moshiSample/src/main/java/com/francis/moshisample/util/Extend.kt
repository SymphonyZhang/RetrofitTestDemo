package com.francis.moshisample.util

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.squareup.moshi.Types

inline fun <reified T> DataResult<T>.toString(): String {
    val parameterizedType = Types.newParameterizedType(DataResult::class.java, T::class.java)
    return MoshiUtil.toJson(this, parameterizedType)
}

inline fun <reified T> String.toDataResult(): DataResult<T>? {
    val parameterizedType = Types.newParameterizedType(DataResult::class.java, T::class.java)
    return MoshiUtil.fromJson<DataResult<T>>(this, parameterizedType)
}

inline fun <reified T> String.toDataResultList(): DataResult<MutableList<T>>? {
    val insideTye = Types.newParameterizedType(List::class.java, T::class.java)
    val parameterizedType = Types.newParameterizedType(DataResult::class.java, insideTye)
    return MoshiUtil.fromJson<DataResult<MutableList<T>>>(this, parameterizedType)
}