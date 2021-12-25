package com.bonepeople.android.widget.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Gson数据转换工具类
 */
object AppGson {
    val gson: Gson by lazy { GsonBuilder().disableHtmlEscaping().create() }

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
        return gson.fromJson(json, R::class.java) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
    }
}