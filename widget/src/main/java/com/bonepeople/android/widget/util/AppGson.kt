package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import com.google.gson.*
import com.google.gson.internal.bind.TreeTypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

/**
 * Gson数据转换工具类
 */
object AppGson {
    var gson: Gson = GsonBuilder().disableHtmlEscaping().create()

    /**
     * 将对象转换成json字符串
     */
    @CheckResult
    fun toJson(data: Any?): String {
        return gson.toJson(data)
    }

    /**
     * 将json字符串转换成对象
     */
    @CheckResult
    inline fun <reified R> toObject(json: String?): R {
        return if (R::class.java.typeParameters.isNotEmpty()) {
            gson.fromJson(json, object : TypeToken<R>() {}.type) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
        } else {
            gson.fromJson(json, R::class.java) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
        }
    }

    /**
     * 为gson添加一个可以去除空值的适配器
     * + 该适配器可以在解析的过程中去掉json字符串中的null，防止空指针的发生
     * @param originGson 需要添加适配器的gson，可以是[AppGson.gson]
     * @return 返回一个已经添加了适配器的gson，可以使用返回的gson进行解析
     */
    @CheckResult
    fun addNotNullAdapter(originGson: Gson): Gson {
        return originGson.newBuilder().registerTypeAdapterFactory(GsonFactory(originGson)).create()
    }

    private class GsonFactory(private val originGson: Gson) : TypeAdapterFactory {
        private val adapter = GsonAdapter(originGson)
        override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
            return TreeTypeAdapter.newFactoryWithMatchRawType(type, adapter).create(originGson, type)
        }
    }

    private class GsonAdapter(private val gson: Gson) : JsonDeserializer<Any> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any {
            if (json?.isJsonObject == true) {
                val obj = json.asJsonObject
                searchInObject(obj)
                return gson.fromJson(obj, typeOfT)
            }
            if (json?.isJsonArray == true) {
                val array = json.asJsonArray
                searchInArray(array)
                return gson.fromJson(array, typeOfT)
            }
            return gson.fromJson(json, typeOfT)
        }

        private fun searchInObject(obj: JsonObject) {
            val nullKey = ArrayList<String>()
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
            arr.forEachIndexed { index, jsonElement ->
                when {
                    jsonElement.isJsonNull -> nullIndex.addFirst(index)
                    jsonElement.isJsonObject -> searchInObject(jsonElement.asJsonObject)
                    jsonElement.isJsonArray -> searchInArray(jsonElement.asJsonArray)
                }
            }
            nullIndex.forEach { arr.remove(it) }
        }
    }
}