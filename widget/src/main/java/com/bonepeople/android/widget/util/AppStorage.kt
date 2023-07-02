package com.bonepeople.android.widget.util

import com.bonepeople.android.widget.ApplicationHolder
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * App数据存储工具类
 *
 * 基于MMKV
 */
@Suppress("UNUSED")
object AppStorage {
    private const val TYPE_STRING = 1
    private const val TYPE_BOOLEAN = 2
    private const val TYPE_INT = 3
    private const val TYPE_LONG = 4
    private const val TYPE_FLOAT = 5

    /**
     * 原始存储实例
     * + 该实例用于将旧数据迁移至新的存储实例中，从1.5.5版本，开始过渡阶段
     * + 预计于1.6.0版本，移除
     * + 过渡阶段会从该实例中读取数据，但不会写入
     */
    private val originStorage: MMKV by lazy { init() }
    private val dataStorage: MMKV by lazy {
        MMKV.initialize(ApplicationHolder.app, MMKVLogLevel.LevelNone)
        val secret: String = AppMessageDigest.md5(ApplicationHolder.getPackageName())
        MMKV.mmkvWithID("${ApplicationHolder.getPackageName()}.data", MMKV.MULTI_PROCESS_MODE, secret).also {
            it.trim()
        }
    }
    private val keyStorage: MMKV by lazy {
        MMKV.initialize(ApplicationHolder.app, MMKVLogLevel.LevelNone)
        val secret: String = AppMessageDigest.md5(ApplicationHolder.getPackageName())
        MMKV.mmkvWithID("${ApplicationHolder.getPackageName()}.key", MMKV.MULTI_PROCESS_MODE, secret).also {
            it.trim()
        }
    }

    /**
     * 多进程支持
     * + 在使用[AppStorage]前通过修改此项来调整多进程支持，默认为启用
     * + 在某些特殊情况下可能会无效，已于1.5.5版本标记为废弃
     * + 预计于1.6.0版本，移除
     */
    @Deprecated("multiProcess在某些特殊情况下可能会无效，现在全部的数据实例均采用多进程方式初始化")
    var multiProcess = true

    private fun init(): MMKV {
        MMKV.initialize(ApplicationHolder.app, MMKVLogLevel.LevelNone)
        val secret: String = AppMessageDigest.md5(ApplicationHolder.getPackageName())
        var mmkv: MMKV? = null
        var version = 0
        val master = MMKV.mmkvWithID("AppStorage", MMKV.MULTI_PROCESS_MODE, secret)
        version = master.getInt("AppStorage.version", version)
        if (version == 0) {
            mmkv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        }
        if (version == 1) {
            mmkv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, secret)
        }
        if (mmkv == null) {
            mmkv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, secret)
        }
        master.encode("AppStorage.version", version)
        mmkv!!.trim()
        return mmkv
    }

    /**
     * 移除指定字段
     */
    fun remove(key: String) {
        originStorage.removeValueForKey(key)
        dataStorage.removeValueForKey(key)
        keyStorage.removeValueForKey(key)
    }

    /**
     * 备份所有数据至文件
     */
    fun backup(target: OutputStream) {
        val file = File(ApplicationHolder.app.cacheDir, "AppStorage.backup")
        file.mkdirs()
        MMKV.backupAllToDirectory(file.absolutePath)
        AppZip.zipFiles(target, file)
        file.listFiles()?.forEach {
            it.delete()
        }
        file.delete()
    }

    /**
     * 从文件恢复所有数据
     */
    fun restore(source: InputStream) {
        val file = File(ApplicationHolder.app.cacheDir, "AppStorage.backup")
        AppZip.unzipFile(source, file.parentFile!!)
        MMKV.restoreAllFromDirectory(file.absolutePath)
        file.listFiles()?.forEach {
            it.delete()
        }
        file.delete()
    }

    /**
     * 存放字符串
     */
    fun putString(key: String, value: String?) {
        putDataInternal(key, value.toString())
    }

    /**
     * 取出字符串，默认为空字符串
     */
    fun getString(key: String, default: String = ""): String {
        return getDataInternal(key, default)
    }

    /**
     * 存放布尔数据
     */
    fun putBoolean(key: String, value: Boolean) {
        putDataInternal(key, value)
    }

    /**
     * 取出布尔数据，默认为false
     */
    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getDataInternal(key, default)
    }

    /**
     * 存放整型数字
     */
    fun putInt(key: String, value: Int) {
        putDataInternal(key, value)
    }

    /**
     * 取出整型数字，默认为0
     */
    fun getInt(key: String, default: Int = 0): Int {
        return getDataInternal(key, default)
    }

    /**
     * 存放长整型数字
     */
    fun putLong(key: String, value: Long) {
        putDataInternal(key, value)
    }

    /**
     * 取出长整型数字，默认为0
     */
    fun getLong(key: String, default: Long = 0): Long {
        return getDataInternal(key, default)
    }

    /**
     * 存放浮点型数字
     */
    fun putFloat(key: String, value: Float) {
        putDataInternal(key, value)
    }

    /**
     * 取出浮点型数字，默认为0
     */
    fun getFloat(key: String, default: Float = 0f): Float {
        return getDataInternal(key, default)
    }

    private fun <T> putDataInternal(key: String, value: T) {
        originStorage.removeValueForKey(key)
        when (value) {
            is String -> {
                dataStorage.putString(key, value)
                keyStorage.putInt(key, TYPE_STRING)
            }

            is Boolean -> {
                dataStorage.putBoolean(key, value)
                keyStorage.putInt(key, TYPE_BOOLEAN)
            }

            is Int -> {
                dataStorage.putInt(key, value)
                keyStorage.putInt(key, TYPE_INT)
            }

            is Long -> {
                dataStorage.putLong(key, value)
                keyStorage.putInt(key, TYPE_LONG)
            }

            is Float -> {
                dataStorage.putFloat(key, value)
                keyStorage.putInt(key, TYPE_FLOAT)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getDataInternal(key: String, default: T): T {
        var data: T = default
        when (default) {
            is String -> {
                if (originStorage.containsKey(key)) {
                    data = (originStorage.decodeString(key, default) ?: default) as T
                    putDataInternal(key, data)
                } else {
                    if (keyStorage.decodeInt(key) == TYPE_STRING) {
                        data = (dataStorage.decodeString(key, default) ?: default) as T
                    } else {
                        throw IllegalArgumentException("数据类型错误")
                    }
                }
            }

            is Boolean -> {
                if (originStorage.containsKey(key)) {
                    data = originStorage.decodeBool(key, default) as T
                    putDataInternal(key, data)
                } else {
                    if (keyStorage.decodeInt(key) == TYPE_BOOLEAN) {
                        data = dataStorage.decodeBool(key, default) as T
                    } else {
                        throw IllegalArgumentException("数据类型错误")
                    }
                }
            }

            is Int -> {
                if (originStorage.containsKey(key)) {
                    data = originStorage.decodeInt(key, default) as T
                    putDataInternal(key, data)
                } else {
                    if (keyStorage.decodeInt(key) == TYPE_INT) {
                        data = dataStorage.decodeInt(key, default) as T
                    } else {
                        throw IllegalArgumentException("数据类型错误")
                    }
                }
            }

            is Long -> {
                if (originStorage.containsKey(key)) {
                    data = originStorage.decodeLong(key, default) as T
                    putDataInternal(key, data)
                } else {
                    if (keyStorage.decodeInt(key) == TYPE_LONG) {
                        data = dataStorage.decodeLong(key, default) as T
                    } else {
                        throw IllegalArgumentException("数据类型错误")
                    }
                }
            }

            is Float -> {
                if (originStorage.containsKey(key)) {
                    data = originStorage.decodeFloat(key, default) as T
                    putDataInternal(key, data)
                } else {
                    if (keyStorage.decodeInt(key) == TYPE_FLOAT) {
                        data = dataStorage.decodeFloat(key, default) as T
                    } else {
                        throw IllegalArgumentException("数据类型错误")
                    }
                }
            }
        }
        return data
    }
}