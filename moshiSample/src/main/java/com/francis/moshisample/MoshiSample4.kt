package com.francis.moshisample

import com.francis.moshisample.util.DataResult
import com.francis.moshisample.util.InsideType
import com.francis.moshisample.util.MidType
import com.francis.moshisample.util.MoshiUtil
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * moshi 处理嵌套数据类型
 */
fun main() {
    val data = DataResult(
        code = 200, msg = "success", data = MidType(
            name = "Store", goodsList = listOf(
                InsideType(id = 0, name = "pen"),
                InsideType(id = 1, name = "water"),
                InsideType(id = 2, name = "apple"),
            )
        )
    )
    val srcJson =
        """{"code":201,"msg":"Ok","data":{"name":"7Eleven","goodsList":[{"id":0,"name":"pear"},{"id":1,"name":"fan"},{"id":2,"name":"box"}]}}"""

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // 序列化和反序列化复杂的数据
    val type = Types.newParameterizedType(DataResult::class.java, MidType::class.java)
    val jsonAdapter1 = moshi.adapter<DataResult<MidType>>(type)
    val toJson1 = jsonAdapter1.toJson(data)
    println("toJson1 => $toJson1")
    println()
    val fromJson1 = jsonAdapter1.fromJson(srcJson)
    println("fromJson1 => $fromJson1")
    println()
    println("==============================================================>")

    // 序列化对象中直接包含List
    val srcJson11 =
        """{"code":201,"msg":"Ok","data":[{"id":0,"name":"pear"},{"id":1,"name":"fan"},{"id":2,"name":"box"}]}"""
    val data1 = DataResult(code = 100, msg = "ok", data = listOf(InsideType(id = 0, name = "table"),InsideType(id = 1, name = "door"),InsideType(id = 2, name = "knife")))
    val type1 = Types.newParameterizedType(DataResult::class.java,List::class.java) //注意这里，因为包含的是List，所以应该是一个List对象，这里就应该填写List的class 而不是List中的Goods类
    val adapter11 = moshi.adapter<DataResult<List<InsideType>>>(type1)
    val toJson11 = adapter11.toJson(data1)
    println("toJson11 ===> $toJson11")
    println()
    val fromJson11 = adapter11.fromJson(srcJson11)
    println("fromJson11  ===> $fromJson11")
    // fromJson11  ===> DataResult1(code=201, msg=Ok, data=[{id=0.0, name=pear}, {id=1.0, name=fan}, {id=2.0, name=box}])
    println()
    // 但是 可以看出data中的数据没有明显的数据类型，这样的话调用会出问题了 解决方法在下一个Sample
    /*fromJson11?.data?.forEach {
        println("it.name ==> ${it.name}") // 报错了！！，
    }*/

    // 使用封装工具类
    println("===============================================================>> ")
    val toJsonWithUtil = MoshiUtil.toJson(data, parameterizedType = type)
    println("toJsonWithUtil ::=> $toJsonWithUtil")
    val fromJsonWithUtil = MoshiUtil.fromJson<DataResult<MidType>>(srcJson,type)
    println("fromJsonWithUtil ::=> $fromJsonWithUtil")
    println()
    val toJsonWithUtil1 = MoshiUtil.toJson(data1, parameterizedType = type1)
    println("toJsonWithUtil1 ::=> $toJsonWithUtil1")
    val fromJsonWithUtil1 = MoshiUtil.fromJson<DataResult<List<InsideType>>>(srcJson11, parameterizedType = type1)
    println("fromJsonWithUtil1 ::=> $fromJsonWithUtil1")
    /*fromJsonWithUtil1?.data?.forEach {
        println("it.name ==> ${it.name}")// 报错了！！，
    }*/
}