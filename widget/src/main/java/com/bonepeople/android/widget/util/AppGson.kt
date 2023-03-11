package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import com.google.gson.*
import com.google.gson.internal.bind.TreeTypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Gson数据转换工具类
 */
@Suppress("UNUSED")
object AppGson {
    var defaultGson = GsonBuilder().disableHtmlEscaping().create()

    /**
     * 将对象转换成json字符串
     */
    @CheckResult
    @JvmOverloads
    fun toJson(data: Any?, gson: Gson = defaultGson): String {
        return gson.toJson(data)
    }

    /**
     * 将json字符串转换成对象
     */
    @CheckResult
    @JvmOverloads
    inline fun <reified R> toObject(json: String?, gson: Gson = defaultGson): R {
        return if (R::class.java.typeParameters.isNotEmpty()) {
            gson.fromJson(json, object : TypeToken<R>() {}.type) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
        } else {
            gson.fromJson(json, R::class.java) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
        }
    }

    /**
     * 为gson添加一个可以去除空值的适配器
     * + 该适配器可以在解析的过程中去掉json字符串中的null，防止空指针的发生
     * @param originGson 需要添加适配器的gson，可以是[AppGson.defaultGson]，去掉null之后将使用此gson进行解析
     * @return 返回一个已经添加了适配器的gson，可以使用返回的gson进行解析
     */
    @CheckResult
    fun addNotNullAdapter(originGson: Gson): Gson {
        return originGson.newBuilder().registerTypeAdapterFactory(GsonFactory(originGson)).create()
    }

    private class GsonFactory(private val originGson: Gson) : TypeAdapterFactory {
        private val adapter = GsonAdapter()
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            return TreeTypeAdapter.newFactoryWithMatchRawType(type, adapter).create(originGson, type)
        }
    }

    private class GsonAdapter : JsonDeserializer<Any>, JsonSerializer<Any> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any {
            if (json.isJsonObject) searchInObject(json.asJsonObject)
            if (json.isJsonArray) searchInArray(json.asJsonArray)
            return context.deserialize(json, typeOfT)
        }

        private fun searchInObject(obj: JsonObject) {
            val nullKey = LinkedList<String>()
            obj.keySet().forEach {
                val element = obj.get(it)
                when {
                    element.isJsonNull -> nullKey.add(it)
                    element.isJsonObject -> searchInObject(element.asJsonObject)
                    element.isJsonArray -> searchInArray(element.asJsonArray)
                }
            }
            nullKey.forEach { obj.remove(it) }
        }

        private fun searchInArray(arr: JsonArray) {
            val nullIndex = LinkedList<Int>()
            arr.forEachIndexed { index, element ->
                when {
                    element.isJsonNull -> nullIndex.addFirst(index)
                    element.isJsonObject -> searchInObject(element.asJsonObject)
                    element.isJsonArray -> searchInArray(element.asJsonArray)
                }
            }
            nullIndex.forEach { arr.remove(it) }
        }

        override fun serialize(src: Any, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return context.serialize(src, typeOfSrc)
        }
    }
}