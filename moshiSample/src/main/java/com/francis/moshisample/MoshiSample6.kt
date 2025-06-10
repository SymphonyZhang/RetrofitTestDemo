package com.francis.moshisample

import com.francis.moshisample.util.InsideType
import com.francis.moshisample.util.Person
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * moshi 字段映射，序列化和反序列化字段忽略 格式化json
 */
fun main() {
    val person = Person(
        name = "zyx",
        age = 20,
        books = arrayListOf(
            InsideType(id = 0, name = "book0"),
            InsideType(id = 1, name = "book1"),
            InsideType(id = 2, name = "book2")
        )
    )
    val srcJson = """{"name_filed":"Tom","age":23,"books":[{"id":3,"name":"book3"},{"id":4,"name":"book4"},{"id":5,"name":"book5"}]}"""

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter(Person::class.java)
    val toJson = adapter.toJson(person)
    println("toJson ==> $toJson") // toJson ==> {"name_filed":"zyx","age":20}
    println()
    val fromJson = adapter.fromJson(srcJson)
    println("fromJson ==> $fromJson") // fromJson ==> Person(name=Tom, age=23, books=[]) 因为books字段被忽略了，所以这里为空
    println()
    val toJson111 = adapter.indent("").toJson(person) // 通过intent 可以格式化json ，空字符就是常用的样式
    val toJson222 = adapter.indent(">").toJson(person) //  也可以用其他字符替代
    println("toJson111 ==> $toJson111")
    println()
    println("toJson111 ==> $toJson222")
    println()
}