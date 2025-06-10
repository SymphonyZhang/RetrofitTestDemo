package com.francis.moshisample

import com.francis.moshisample.util.DataResult
import com.francis.moshisample.util.InsideType
import com.francis.moshisample.util.MoshiUtil
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * moshi 处理泛型
 */
@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val srcJson = """{"code":100,"msg":"test110","data":{"id":2,"name":"Alex"}}"""
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val data = InsideType(0,"Ella")
    val success = DataResult(code = 1, msg = "success", data =data)

    // 因为是泛型，不能按普通类型使用，
    /*
    val jsonAdapter = moshi.adapter(DataResult.Success::class.java)
    val toJson = jsonAdapter.toJson(success)
    println("toJson => $toJson")
    println()
    */

    // 同理，List就是泛型，所以可以按照List的使用方式使用
    val success11 = DataResult(code = 0, msg = "test",data= InsideType(1,"BeBe"))
    val type = Types.newParameterizedType(DataResult::class.java, InsideType::class.java)
    val jsonAdapter11 = moshi.adapter<DataResult<InsideType>>(type)
    val toJson11 = jsonAdapter11.toJson(success11)
    println("toJson11 => $toJson11")
    println()
    val fromJson = jsonAdapter11.fromJson(srcJson)
    println("fromJson => $fromJson")
    println()

    // 使用实验API
    val jsonAdapterApi = moshi.adapter<DataResult<InsideType>>()
    val toJson1 = jsonAdapterApi.toJson(success)
    println("toJson1 => $toJson1")
    println()
    val fromJson1 = jsonAdapterApi.fromJson(srcJson)
    println("fromJson1 => $fromJson1")
    println()


    // 使用封装工具类
    println("===============================================================>> ")
    val toJsonWithUtil = MoshiUtil.toJson(success11, parameterizedType = type)
    println("toJsonWithUtil ::=> $toJsonWithUtil")
    val fromJsonWithUtil = MoshiUtil.fromJson<DataResult<InsideType>>(srcJson, parameterizedType = type)
    println("fromJsonWithUtil ::=> $fromJsonWithUtil")
    println()
    println("===============================================================>>> ")
    success11.toString()
}