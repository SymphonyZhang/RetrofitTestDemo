package com.francis.moshisample.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

/**
 * moshi 的 json 转换封装
 */
object MoshiUtil {
    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    fun <T> toJson(adapter: JsonAdapter<T>, data: T, indent: String = ""): String {
        try {
            return adapter.indent(indent).toJson(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    inline fun <reified T> toJson(data: T, indent: String = ""): String {
        val adapter = moshi.adapter(T::class.java)
        return this.toJson(adapter = adapter, data = data, indent = indent)
    }

    inline fun <reified T> toJson(
        data: T,
        parameterizedType: ParameterizedType,
        indent: String = ""
    ): String {
        val adapter = moshi.adapter<T>(parameterizedType)
        return this.toJson(adapter = adapter, data = data, indent = indent)
    }

    inline fun <reified T> fromJson(adapter: JsonAdapter<T>, jsonStr: String): T? {
        try {
            return adapter.fromJson(jsonStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> fromJson(jsonStr: String): T? {
        val adapter = moshi.adapter(T::class.java)
        return this.fromJson(adapter = adapter, jsonStr = jsonStr)
    }


    inline fun <reified T> fromJsonToMutableList(jsonStr: String): MutableList<T>? {
        val type = Types.newParameterizedType(MutableList::class.java, T::class.java)
        return this.fromJson<MutableList<T>>(jsonStr, type)
    }

    inline fun <reified T> fromJsonToList(jsonStr: String): List<T>? {
        val type = Types.newParameterizedType(MutableList::class.java, T::class.java)
        return this.fromJson<List<T>>(jsonStr, type)
    }

    inline fun <reified T> fromJson(jsonStr: String, parameterizedType: ParameterizedType): T? {
        val adapter = moshi.adapter<T>(parameterizedType)
        return this.fromJson(adapter = adapter, jsonStr = jsonStr)
    }

}