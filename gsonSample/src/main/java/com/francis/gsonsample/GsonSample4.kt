package com.francis.gsonsample

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Gson序列化 List 数据 基本方法
 */
data class GoodsInfo4(
    val id: Long,
    val goodsName: String
)

fun main() {
    val gson = GsonBuilder().create()

    val jsonStr = """[{"id":1,"goodsName":"Pen"},{"id":2,"goodsName":"Pencil"},{"id":3,"goodsName":"Notebook"}]"""
    println("Source Json Data : => $jsonStr")
    val listType = object : TypeToken<ArrayList<GoodsInfo4>>(){}.type //拿到TypeToken
    val goodsList:ArrayList<GoodsInfo4> = gson.fromJson(jsonStr,listType) // 通过TypeToken反序列化
    println("Deserialized data : ==> $goodsList")

    println("===================================================================>")

    val goods = listOf(GoodsInfo4(id = 4, goodsName = "phone"),GoodsInfo4(id = 5, goodsName = "cup"),GoodsInfo4(id = 6, goodsName = "tea"))
    println("Source Data : => $goods")
    val jsonStr1 = gson.toJson(goods)
    println("Serialized Data : ==> $jsonStr1")

}