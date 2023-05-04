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
    private val storage: MMKV by lazy { init() }

    /**
     * 多进程支持
     * + 在使用[AppStorage]前通过修改此项来调整多进程支持，默认为启用
     */
    var multiProcess = true

    private fun init(): MMKV {
        MMKV.initialize(ApplicationHolder.app, MMKVLogLevel.LevelNone)
        val mode = if (multiProcess) MMKV.MULTI_PROCESS_MODE else MMKV.SINGLE_PROCESS_MODE
        val secret: String = AppEncrypt.encryptByMD5(ApplicationHolder.getPackageName())
        var mmkv: MMKV? = null
        var version = 0
        val master = MMKV.mmkvWithID("AppStorage", mode, secret)
        version = master.getInt("AppStorage.version", version)
        if (version == 0) {
            mmkv = MMKV.defaultMMKV(mode, null)
            mmkv?.reKey(secret)
            version = 1
        }
        if (mmkv == null) {
            mmkv = MMKV.defaultMMKV(mode, secret)
        }
        master.encode("AppStorage.version", version)
        mmkv!!.trim()
        return mmkv
    }

    /**
     * 移除指定字段
     */
    fun remove(key: String) {
        storage.removeValueForKey(key)
    }

    /**
     * 备份所有数据至文件
     */
    fun backup(target: OutputStream) {
        val file = File(ApplicationHolder.app.cacheDir, "AppStorage.backup")
        file.mkdirs()
        storage.sync()
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
        storage.sync()
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
        storage.putString(key, value)
    }

    /**
     * 取出字符串，默认为空字符串
     */
    fun getString(key: String, default: String = ""): String {
        return storage.getString(key, default) ?: default
    }

    /**
     * 存放布尔数据
     */
    fun putBoolean(key: String, value: Boolean) {
        storage.putBoolean(key, value)
    }

    /**
     * 取出布尔数据，默认为false
     */
    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return storage.getBoolean(key, default)
    }

    /**
     * 存放整型数字
     */
    fun putInt(key: String, value: Int) {
        storage.putInt(key, value)
    }

    /**
     * 取出整型数字，默认为0
     */
    fun getInt(key: String, default: Int = 0): Int {
        return storage.getInt(key, default)
    }

    /**
     * 存放长整型数字
     */
    fun putLong(key: String, value: Long) {
        storage.putLong(key, value)
    }

    /**
     * 取出长整型数字，默认为0
     */
    fun getLong(key: String, default: Long = 0): Long {
        return storage.getLong(key, default)
    }

    /**
     * 存放浮点型数字
     */
    fun putFloat(key: String, value: Float) {
        storage.putFloat(key, value)
    }

    /**
     * 取出浮点型数字，默认为0
     */
    fun getFloat(key: String, default: Float = 0f): Float {
        return storage.getFloat(key, default)
    }
}