package com.francis.gsonsample

import com.google.gson.GsonBuilder

/**
 * json 和 kotlin 配合的问题
 */

data class Student(
    val no: Int,
    val name: String
)

data class Student1(
    val no: Int,
    val name: String = "abc"
)

data class Student2(
    val no: Int = 0,
    val name: String = "abc"
)

fun main() {
    val gson = GsonBuilder().create()

    // 正常
    val jsonStr = """{"no":0,"name":"zyx"}"""
    val student = gson.fromJson(jsonStr,Student::class.java)
    println("student ==> $student") // Student(no=0, name=zyx)
    println()
    // 当 json 中缺失了某个属性值时，就会导致反序列化出来的对象的属性纯在null，即便是 非空类型
    // 这里就是Gson配合Kotlin data class 反序列化的一个bug问题，明明是非空类型，结果赋值为null，要直到调用才会发现问题所在
    // 这样就会影响对象在被调用时的问题，一个不注意就会引发 `NullPointerException`
    // 解决方法一 : 为了避免被调用时引发bug而不知，可以把可能为null的字段设置为可空类型 就是加上 ?
    val jsonStr1 = """{"no":1}"""
    val student1 = gson.fromJson(jsonStr1,Student::class.java)
    println("student ==> $student1") //Student(no=1, name=null)
    println()

    // 解决方法二 : 给每个参数都添加默认值，注意是每一个参数都要，不能只给可能为空参数添加默认值
    // 尝试只给可能为空参数添加默认值
    val jsonStr2 = """{"no":2}"""
    val student2 = gson.fromJson(jsonStr2,Student1::class.java)
    println("student ==> $student2") //Student1(no=2, name=null) // 可以看出 只给可能为空参数添加默认值 ，还是会被赋值为null
    println()

    // 给每个参数都添加默认值
    val jsonStr3 = """{"no":3}"""
    val student3 = gson.fromJson(jsonStr3,Student2::class.java)
    println("student ==> $student3") //Student2(no=3, name=abc) // 成功把 缺失的字段用默认值补上了
    println()


    //TODO 但是要注意一种情况，就是返回的字段全了，但是字段的值就是null
    // 这种只能跟数据来源方协商了，或者换一种json解析库，如jackson, Moshi, KotlinX
    val jsonStr4 = """{"no":4,"name":null}"""
    val student4 = gson.fromJson(jsonStr4,Student2::class.java)
    println("student ==> $student4") //Student2(no=4, name=null)
    println()
}