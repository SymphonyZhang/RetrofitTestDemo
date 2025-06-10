package com.francis.moshisample.util

import com.squareup.moshi.Json

data class TestData(
    val no: Int,
    val name: String = "Alex"
)

data class DataResult<T> (
    val code: Int,
    val msg: String,
    val data: T,
)

data class MidType(
    val name: String,
    val goodsList: List<InsideType>
)

data class InsideType(
    val id: Int,
    val name: String,
)

data class Person(
    @Json(name = "name_filed") //使json的key变成 name_filed
    val name: String,
    val age: Int,
    @Json(ignore = true) // 忽略掉这个字段
    val books: List<InsideType> = arrayListOf()
)

sealed interface NetworkResponse<out D,out E> {
    data class Success<out T>(val data:T):NetworkResponse<T,Nothing>
    data class Error<out E>(val error:E):NetworkResponse<Nothing,E>
}