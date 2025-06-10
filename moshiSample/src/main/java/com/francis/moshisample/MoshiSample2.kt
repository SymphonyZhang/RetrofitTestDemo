package com.francis.moshisample

import com.francis.moshisample.util.InsideType
import com.francis.moshisample.util.MoshiUtil
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * moshi 序列化和反序列化 List 数据
 */

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val srcJson = """[{"id":4,"name":"keyboard"},{"id":5,"name":"mouse"},{"id":6,"name":"glass"}]"""
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val goods = listOf(InsideType(1,"pen"),InsideType(2,"phone"),InsideType(3,"water"))
    //方法一 使用了实验性质的api
    val goodsAdapter = moshi.adapter<List<InsideType>>()
    val toJson1 = goodsAdapter.toJson(goods)
    println("toJson1 => $toJson1")
    println()

    val fromJson = goodsAdapter.fromJson(srcJson)
    println("fromJson => $fromJson")
    fromJson?.onEach {
        println("it.name => ${it.name}")
    }
    println()

    // 方法二 标准做法
    val type = Types.newParameterizedType(List::class.java, InsideType::class.java)
    val goodsAdapter1 = moshi.adapter<List<InsideType>>(type)
    val toJson2 = goodsAdapter1.toJson(goods)
    println("toJson2 => $toJson2")
    println()

    val fromJson2 = goodsAdapter1.fromJson(srcJson)
    println("fromJson2 => $fromJson2")
    fromJson2?.forEach {
        println("it.name => ${it.name}")
    }
    println()

// 使用封装工具类
    println("===============================================================>> ")
    val toJsonWithUtil = MoshiUtil.toJson(goods)
    println("toJsonWithUtil ::=> $toJsonWithUtil")
    val fromJsonWithUtil = MoshiUtil.fromJsonToList<InsideType>(srcJson)
    println("fromJsonWithUtil ::=> $fromJsonWithUtil")

}