package com.francis.moshisample

import com.francis.moshisample.util.DataResult
import com.francis.moshisample.util.InsideType
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * moshi处理复杂数据中直接包含List时的处理
 */
fun main() {
    // 序列化对象中直接包含List
    val srcJson11 =
        """{"code":201,"msg":"Ok","data":[{"id":0,"name":"pear"},{"id":1,"name":"fan"},{"id":2,"name":"box"}]}"""
    val data1 = DataResult(code = 100, msg = "ok", data = listOf(InsideType(id = 0, name = "table"),InsideType(id = 1, name = "door"),InsideType(id = 2, name = "knife")))

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // 因为没有传递到List里面的类型，所以可以通过套娃方式，先弄List层，再把List层套到外层类型中
    val type0 = Types.newParameterizedType(List::class.java, InsideType::class.java) //
    val type1 = Types.newParameterizedType(DataResult::class.java,type0)

    val adapter11 = moshi.adapter<DataResult<List<InsideType>>>(type1)
    val toJson11 = adapter11.toJson(data1)
    println("toJson11 ===> $toJson11")
    println()
    val fromJson11 = adapter11.fromJson(srcJson11)
    println("fromJson11  ===> $fromJson11")
    println()
    fromJson11?.data?.forEach {
        println("it.name ==> ${it.name}")
    }
}