package com.francis.gsonsample

import com.francis.gsonsample.util.GsonUtil

/**
 * 使用封装的Gson工具的效果
 */
data class GoodsInfo5(
    val id: Long,
    val goodsName: String
)

fun main() {
    val jsonStr = """[{"id":1,"goodsName":"Pen"},{"id":2,"goodsName":"Pencil"},{"id":3,"goodsName":"Notebook"}]"""
    val goodsList = GsonUtil.fromJsonToList<ArrayList<GoodsInfo5>>(jsonStr)
    println("Source Json Data : => $jsonStr")
    println("Deserialized data : ==> $goodsList")

    goodsList.forEach {
        println(it.goodsName) // 因为工具类中 使用了reified 使得运行时访问到泛型的具体类型，避免了类型擦除导致的bug
    }

    println("===================================================================>")

    val jsonStr1 = """{"id":31,"goodsName":"ruler"}"""
    val goods = GsonUtil.fromJson<GoodsInfo5>(jsonStr1)
    println("Deserialized data : ==> $goods")

}