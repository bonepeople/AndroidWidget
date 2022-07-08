package com.bonepeople.android.widget.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Gson数据转换工具类
 */
object AppGson {
    var gson: Gson = GsonBuilder().disableHtmlEscaping().create()

    /**
     * 将对象转换成json字符串
     */
    fun toJson(data: Any?): String {
        return gson.toJson(data)
    }

    /**
     * 将json字符串转换成对象
     */
    inline fun <reified R> toObject(json: String?): R {
        return gson.fromJson(json, object : TypeToken<R>() {}.type) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
    }
}