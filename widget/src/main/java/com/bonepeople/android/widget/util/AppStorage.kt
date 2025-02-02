package com.bonepeople.android.widget.util

import com.bonepeople.android.widget.ApplicationHolder
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * App data storage utility class
 *
 * Based on MMKV
 */
@Suppress("Unused")
@Deprecated("The underlying storage implementation has migrated from MMKV to DataStore. Please migrate your data to AppData. This class will be removed from version 1.7.0.")
object AppStorage {
    private const val TYPE_STRING = 1
    private const val TYPE_BOOLEAN = 2
    private const val TYPE_INT = 3
    private const val TYPE_LONG = 4
    private const val TYPE_FLOAT = 5

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
     * Remove a specific key
     */
    fun remove(key: String) {
        dataStorage.removeValueForKey(key)
        keyStorage.removeValueForKey(key)
    }

    /**
     * Backup all data to a file
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
     * Restore all data from a file
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
     * Save a string
     */
    fun putString(key: String, value: String?) {
        putDataInternal(key, value.toString())
    }

    /**
     * Retrieve a string, default is an empty string
     */
    fun getString(key: String, default: String = ""): String {
        return getDataInternal(key, default)
    }

    /**
     * Save a boolean value
     */
    fun putBoolean(key: String, value: Boolean) {
        putDataInternal(key, value)
    }

    /**
     * Retrieve a boolean value, default is false
     */
    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getDataInternal(key, default)
    }

    /**
     * Save an integer value
     */
    fun putInt(key: String, value: Int) {
        putDataInternal(key, value)
    }

    /**
     * Retrieve an integer value, default is 0
     */
    fun getInt(key: String, default: Int = 0): Int {
        return getDataInternal(key, default)
    }

    /**
     * Save a long value
     */
    fun putLong(key: String, value: Long) {
        putDataInternal(key, value)
    }

    /**
     * Retrieve a long value, default is 0
     */
    fun getLong(key: String, default: Long = 0): Long {
        return getDataInternal(key, default)
    }

    /**
     * Save a float value
     */
    fun putFloat(key: String, value: Float) {
        putDataInternal(key, value)
    }

    /**
     * Retrieve a float value, default is 0
     */
    fun getFloat(key: String, default: Float = 0f): Float {
        return getDataInternal(key, default)
    }

    private fun <T> putDataInternal(key: String, value: T) {
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
                if (keyStorage.decodeInt(key, TYPE_STRING) == TYPE_STRING) {
                    data = (dataStorage.decodeString(key, default) ?: default) as T
                } else {
                    throw IllegalArgumentException("Incorrect data type")
                }
            }

            is Boolean -> {
                if (keyStorage.decodeInt(key, TYPE_BOOLEAN) == TYPE_BOOLEAN) {
                    data = dataStorage.decodeBool(key, default) as T
                } else {
                    throw IllegalArgumentException("Incorrect data type")
                }
            }

            is Int -> {
                if (keyStorage.decodeInt(key, TYPE_INT) == TYPE_INT) {
                    data = dataStorage.decodeInt(key, default) as T
                } else {
                    throw IllegalArgumentException("Incorrect data type")
                }
            }

            is Long -> {
                if (keyStorage.decodeInt(key, TYPE_LONG) == TYPE_LONG) {
                    data = dataStorage.decodeLong(key, default) as T
                } else {
                    throw IllegalArgumentException("Incorrect data type")
                }
            }

            is Float -> {
                if (keyStorage.decodeInt(key, TYPE_FLOAT) == TYPE_FLOAT) {
                    data = dataStorage.decodeFloat(key, default) as T
                } else {
                    throw IllegalArgumentException("Incorrect data type")
                }
            }
        }
        return data
    }
}