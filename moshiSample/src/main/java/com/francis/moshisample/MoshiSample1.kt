package com.francis.moshisample

import com.francis.moshisample.util.InsideType
import com.francis.moshisample.util.MoshiUtil
import com.francis.moshisample.util.TestData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Moshi的正常基本使用
 */



fun main() {
    // Step1 创建moshi
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())//使用kotlin反射处理
        .build()

    // 创建对象
    val student = InsideType(0, "abc")
    // Step2 声明adapter ， 指定要处理的类型
    val jsonAdapter = moshi.adapter(InsideType::class.java)
    // 序列化
    val toJson = jsonAdapter.toJson(student)
    println("toJson ::=> $toJson")
    println()
    //反序列化
    val jsonStr = """{"id":1,"name":"zyx"}"""
    val fromJson = jsonAdapter.fromJson(jsonStr)
    println("fromJson ::=> $fromJson")
    println()

    // 当反序列化时，接收到的json缺失了字段的状况
    /*
    val jsonStr1 = """{"id":0}"""
    val fromJson1 = jsonAdapter.fromJson(jsonStr1) // 直接在这里就报了Required value 'name'的空异常了，不像Gson那样向一个非空类型赋值null
    println("fromJson ::=> $fromJson1")
    */

    // 在接收到的json缺失字段的情况下，可以用默认值补上
    val jsonTeacher1 = """{"no":0}"""
    val teacherJsonAdapter = moshi.adapter(TestData::class.java)
    val teacher = teacherJsonAdapter.fromJson(jsonTeacher1)
    println("teacher from Json => $teacher")
    println()

    // 当接收到的json某些字段值为null时
    /*val jsonTeacher2 = """{"no":0,"name":null}"""
    val teacher2 = teacherJsonAdapter.fromJson(jsonTeacher2)// 直接在这里就报了Non-null value 'name' was null at $.name的异常了，不像Gson那样向一个非空类型赋值null
    println("teacher from Json => $teacher2")
    println()*/

    // 使用封装工具类
    println("===============================================================>> ")
    val toJsonWithUtil = MoshiUtil.toJson(student)
    println("toJsonWithUtil ::=> $toJsonWithUtil")
    val fromJsonWithUtil = MoshiUtil.fromJson<InsideType>(jsonStr)
    println("fromJsonWithUtil ::=> $fromJsonWithUtil")
}