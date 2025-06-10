package com.francis.moshisample

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Base64

/**
 * 自定义JsonAdapter
 */
// 枚举类
enum class CustomType(val type: Int, val content: String) {
    DEFAULT(0, "DEFAULT"),
    BAD(1, "BAD"),
    NORMAL(2, "NORMAL"),
    GOOD(3, "GOOD");

    companion object {
        fun fromInt(type: Int) = entries.find { it.type == type } ?: DEFAULT
    }
}

// 目标实体
data class TargetBean(
    val type: CustomType,
    val isGood: Boolean,
    val title: String,
)

/**
 * 把 json 中的Int 类型和 CustomType 类型做转换
 */
class TypeAdapter {
    @FromJson
    fun fromJson(value: Int): CustomType {
        println("p0 ==>  $value")
        val type = when (value) {
            CustomType.DEFAULT.type -> CustomType.DEFAULT
            CustomType.BAD.type -> CustomType.BAD
            CustomType.NORMAL.type -> CustomType.NORMAL
            CustomType.GOOD.type -> CustomType.GOOD
            else -> CustomType.DEFAULT
        }
        return type
    }

    @ToJson
    fun toJson(customType: CustomType?): Int {
        return customType?.let { it.type } ?: CustomType.DEFAULT.type
    }
}

/**
 * 把 json 中的Int 类型和 Boolean 类型做转换
 */
class BooleanAdapter {
    @FromJson
    fun fromJson(value: Int): Boolean {
        println("value ==> $value")
        return value != 0
    }

    @ToJson
    fun toJson(value: Boolean): Int {
        return if (value) 1 else 0
    }
}

/**
 * 把 json 中的String 类型用Base64进行编码和解码
 */
class StringAdapter {
    @SuppressLint("NewApi")
    @FromJson
    fun fromJson(value: String): String {
        println("value ==> $value")
        val decode = Base64.getDecoder().decode(value)
        return decode.toString(charset = Charsets.UTF_8)
        // 下面的是在Android SDK 环境中使用
        /*val decode = Base64.decode(value, Base64.NO_WRAP)
        return decode.toString(charset = Charsets.UTF_8)*/
    }

    @SuppressLint("NewApi")
    @ToJson
    fun toJson(value: String): String {
        val encode = Base64.getEncoder().encodeToString(value.toByteArray(charset = Charsets.UTF_8))
        return encode
        // 下面的是在Android SDK 环境中使用
        /*val encode = Base64.encodeToString(value.toByteArray(), Base64.NO_WRAP)
        return encode*/
    }
}

/**
 * 把 json 中需要处理的类型都在这个Adapter中处理了
 */
class TargetBeanAdapter : JsonAdapter<TargetBean>() {
    override fun fromJson(reader: JsonReader): TargetBean {
        var type = CustomType.DEFAULT
        var isGood = false
        var title = ""
        reader.beginObject()
        println("reader ===> ==> $reader")
        while (reader.hasNext()) {
            val name = reader.nextName() // 拿 key
            println("name ==> $name")
            // 通过Key 处理每个需要处理的Key的Value
            when (name) {
                "type" -> type = CustomType.fromInt(reader.nextInt())
                "isGood" -> isGood = (reader.nextInt() != 0)
                "title" -> title = decodeBase64(reader.nextString())
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        //把处理过的Value生成一个Bean并返回
        return TargetBean(
            type = type,
            isGood = isGood,
            title = title
        )
    }

    override fun toJson(writer: JsonWriter, bean: TargetBean?) {
        bean?.let {
            writer.beginObject()
            writer.name("type").value(bean.type.type)
            writer.name("isGood").value(if(bean.isGood) 1 else 0)
            writer.name("title").value(encodeBase64(bean.title))
            writer.endObject()
        } ?: throw NullPointerException("value was null")
    }

    @SuppressLint("NewApi")
    private fun decodeBase64(value:String):String =
        Base64.getDecoder().decode(value).toString(charset = Charsets.UTF_8)

    @SuppressLint("NewApi")
    private fun encodeBase64(value:String): String =
        Base64.getEncoder().encodeToString(value.toByteArray(charset = Charsets.UTF_8))
}


fun main() {
    val srcJson = """{"type":1,"isGood":1,"title":"TW9zaGkgaXMgZmxleGlibGU="}"""

    val moshi = Moshi.Builder()
        /*.add(TypeAdapter())
        .add(BooleanAdapter())
        .add(StringAdapter())*/
        .add(TargetBean::class.java, TargetBeanAdapter()) // 这一行就可以替代上面三行了
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val jsonAdapter = moshi.adapter(TargetBean::class.java)

    val targetBean = jsonAdapter.fromJson(srcJson)
    println("=========Deserialize ========")
    println("==srcJson== :: $srcJson")
    println("==targetBean== :: $targetBean")

    val srcBean = TargetBean(
        type = CustomType.DEFAULT,
        isGood = false,
        title = "abc"
    )
    val targetJson = jsonAdapter.toJson(srcBean)
    println("=========Serialize ========")
    println("srcBean ==> $srcBean")
    println("targetJson ==> $targetJson")
}