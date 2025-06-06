package com.francis.gsonsample

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.annotations.Expose
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Gson序列化数据时对某些数据进行处理
 */
data class GoodsInfo3(
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

/**
 * 实体类转json(序列化)时对 BigDecimal 类型的值进行处理
 */
class BigDecimalSerializer : JsonSerializer<BigDecimal> {
    override fun serialize(
        src: BigDecimal,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        // 四舍五入
        val newVal = src.setScale(2, RoundingMode.HALF_UP)
        // 返回处理好的数据
        return JsonPrimitive(newVal)
    }

}

/**
 * Json转实体类(反序列化)时对 BigDecimal 类型的值进行处理
 */
class BigDecimalDeSerializer : JsonDeserializer<BigDecimal> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BigDecimal {
        // 四舍五入
        val newVal = json.asBigDecimal.setScale(2, RoundingMode.HALF_UP)
        // 返回处理好的数据
        return newVal
    }

}

/**
 * 实体类转json(序列化)时对 String 类型的值进行处理
 */
class StringSerializer : JsonSerializer<String> {
    override fun serialize(
        src: String,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val newVal = "New - $src"
        return JsonPrimitive(newVal)
    }
}

/**
 * Json转实体类(反序列化)时对 String 类型的值进行处理
 */
class StringDeSerializer : JsonDeserializer<String> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        val newVal = "Go-${json.asString}"
        return newVal
    }

}

fun main() {
    val gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        // 通过 registerTypeAdapter 添加我们写的处理类
        .registerTypeAdapter(BigDecimal::class.java,BigDecimalSerializer())
        .registerTypeAdapter(BigDecimal::class.java,BigDecimalDeSerializer())
        .registerTypeAdapter(String::class.java,StringSerializer())
        .registerTypeAdapter(String::class.java,StringDeSerializer())
        .create()
    val goodsInfo = GoodsInfo3(
        id = 1,
        goodsName = "Laptop",
        price = BigDecimal.valueOf(8999.9153),
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
    val jsonStr = """{"id":3,"goodsName":"Fans","price":8999.92634,"createBy":"Tom","modifyBy":"Tim"}"""
    println("Source Json Data : => $jsonStr")
    val goodsInfo1 = gson.fromJson(jsonStr, GoodsInfo3::class.java)
    println("Deserialized data : ==> $goodsInfo1")


}