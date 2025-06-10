package com.francis.moshisample

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.rawType
import java.lang.reflect.Type

/**
 * 自定义Factory
 */

sealed class Shape {
    abstract val name: String

    data class Circle(
        override val name: String,
        val radius: Double
    ) : Shape()

    data class Square(
        override val name: String,
        val side: Double
    ) : Shape()
}

class ShapeAdapterFactory: JsonAdapter.Factory{
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        // 判断是否是Shape类型，因为这里仅仅处理Shape类型
        println("type ==> ${type.rawType}")
        if(Shape::class.java != type.rawType) return null
        val circleAdapter = moshi.adapter(Shape.Circle::class.java)
        val squareAdapter = moshi.adapter(Shape.Square::class.java)
        return PolymorphicJsonAdapterFactory.of(Shape::class.java,"type")
            .withSubtype(Shape.Circle::class.java,"circle")
            .withSubtype(Shape.Square::class.java,"square")
            .create(type,annotations,moshi)
    }

}



fun main() {
    val moshi = Moshi.Builder()
        .add(ShapeAdapterFactory())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val shapes:List<Shape> = listOf(
        Shape.Circle("Pizza",10.0),
        Shape.Square("Box",5.0)
    )

    val adapter = moshi.adapter<List<Shape>>(Types.newParameterizedType(List::class.java,Shape::class.java))

    val json = adapter
        //.indent(" ")
        .toJson(shapes)
    println("=========Serialize ========")
    println("===> src bean ==> $shapes")
    println("===> json ==> $json")
    println()

    println("=========Deserialize ========")
    val srcJson = """[{"type":"circle","name":"Pizza","radius":10.0},{"type":"square","name":"Box","side":5.0},{"type":"circle","name":"Fans","radius":13.0}]"""
    val bean = adapter.fromJson(srcJson)
    println("===> src json ==> $srcJson")
    println("===> bean ==> $bean")
}