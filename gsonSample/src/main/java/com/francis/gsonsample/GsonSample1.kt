package com.francis.gsonsample

import com.google.gson.GsonBuilder
import java.math.BigDecimal

/**
 * 正常的Gson序列化数据
 */
data class GoodsInfo1(
    val id: Long,
    val goodsName: String,
    val price: BigDecimal,
    val enabled: Boolean,
    val createBy: String,
    val modifyBy: String,
)

fun main() {
    val gson = GsonBuilder().create()
    val goodsInfo1 = GoodsInfo1(
        id = 1,
        goodsName = "Laptop",
        price = BigDecimal.valueOf(7699L),
        enabled = true,
        createBy = "zyx",
        modifyBy = "sam"
    )
    // gson 序列化
    println("Source Data : => $goodsInfo1") // 源数据
    val jsonStr1 = gson.toJson(goodsInfo1)
    println("Serialized Data : ==> $jsonStr1")

    println("===================================================================>")

    // gson 反序列化
    val jsonStr = """{"id":2,"goodsName":"Pen","price":10,"enabled":false,"createBy":"Tom","modifyBy":"Tim"}"""
    println("Source Json Data : => $jsonStr")
    val goodsInfo2 = gson.fromJson(jsonStr, GoodsInfo1::class.java)
    println("Deserialized data : ==> $goodsInfo2")
}