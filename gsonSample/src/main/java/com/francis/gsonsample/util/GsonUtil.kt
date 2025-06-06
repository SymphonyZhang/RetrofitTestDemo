package com.francis.gsonsample.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.google.gson.reflect.TypeToken

/**
 * 把Gson 序列化和反序列化封装成一个单例工具
 */
object GsonUtil {
    val gson: Gson = GsonBuilder()
        .serializeNulls()
        .setStrictness(Strictness.LENIENT)
        .create()

    // 使用 reified 具体化的类型参数 避免类型擦除导致的bug
    inline fun <reified T> fromJsonToList(json: String): T {
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }

    // 使用 reified 具体化的类型参数 避免类型擦除导致的bug
    inline fun <reified T> fromJson(json: String): T {
        return gson.fromJson(json, TypeToken.get(T::class.java))
    }

    fun toJson(src: Any): String {
        return gson.toJson(src)
    }
}