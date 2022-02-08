package com.bonepeople.android.widget.util

import com.bonepeople.android.widget.ApplicationHolder
import com.tencent.mmkv.MMKV

/**
 * App数据存储工具类
 *
 * 基于MMKV
 */
object AppStorage {
    private val storage: MMKV? by lazy {
        MMKV.initialize(ApplicationHolder.instance)
        MMKV.defaultMMKV()
    }

    /**
     * 存放字符串
     */
    fun putString(key: String, value: String?) {
        storage?.putString(key, value)
    }

    /**
     * 取出字符串，默认为空字符串
     */
    fun getString(key: String, default: String = ""): String {
        return storage?.getString(key, default) ?: default
    }

    /**
     * 存放布尔数据
     */
    fun putBoolean(key: String, value: Boolean) {
        storage?.putBoolean(key, value)
    }

    /**
     * 取出布尔数据，默认为false
     */
    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return storage?.getBoolean(key, default) ?: default
    }

    /**
     * 存放整型数字
     */
    fun putInt(key: String, value: Int) {
        storage?.putInt(key, value)
    }

    /**
     * 取出整型数字，默认为0
     */
    fun getInt(key: String, default: Int = 0): Int {
        return storage?.getInt(key, default) ?: default
    }
}