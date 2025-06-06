package com.francis.gsonsample

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.math.BigDecimal

/**
 * 正常的Gson序列化数据,排除一些字段
 * 使用`@Expose` 字段标记需要的字段，配合 `excludeFieldsWithoutExposeAnnotation()`
 */
data class GoodsInfo2(
    @Expose
    val id: Long,
    @Expose
    val goodsName: String,
    @Expose
    val price: BigDecimal,
    @Expose
    val enabled: Boolean,
    val createBy: String,
    val modifyBy: String,
)

fun main() {
    val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    val goodsInfo = GoodsInfo2(
        id = 1,
        goodsName = "Laptop",
        price = BigDecimal.valueOf(8999L),
        enabled = true,
        createBy = "zyx",
        modifyBy = "sam"
    )
    // gson 序列化数据
    println("Source Data : ==> $goodsInfo")
    val jsonStr2 = gson.toJson(goodsInfo)
    println("Serialized Data :$jsonStr2")

    println("===================================================================>")

    // gson 反序列化
    val jsonStr = """{"id":2,"goodsName":"Pen","enabled":false,"createBy":"Tom","modifyBy":"Tim"}"""
    println("Source Json Data : => $jsonStr")
    val goodsInfo1 = gson.fromJson(jsonStr, GoodsInfo2::class.java)
    println("Deserialized data : ==> $goodsInfo1")// GoodsInfo2(id=2, goodsName=Pen, price=null, enabled=false, createBy=null, modifyBy=null)
    // 可以看出 jsonStr 中没有的字段和没有被 @Expose 注解标记的字段 都被忽略掉了，被赋值为null
    // [在data class 的 非空字段赋值为null，这就是gson 配合kotlin 的 data class 的一个坑，要重点注意]

}