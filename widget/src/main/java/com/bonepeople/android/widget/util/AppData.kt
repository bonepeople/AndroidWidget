package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.bonepeople.android.widget.ApplicationHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.File

/**
 * AppData
 *
 * a key-value data storage utility class based on Jetpack DataStore
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppDensity)
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
class AppData private constructor(name: String) {
    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            File(ApplicationHolder.app.filesDir, "appdata/$name/data.preferences_pb")
        }
    }
    private val configStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            File(ApplicationHolder.app.filesDir, "appdata/$name/config.preferences_pb")
        }
    }

    suspend fun putString(key: String, value: String) = putData(stringPreferencesKey(key), value)
    suspend fun putInt(key: String, value: Int) = putData(intPreferencesKey(key), value)
    suspend fun putLong(key: String, value: Long) = putData(longPreferencesKey(key), value)
    suspend fun putFloat(key: String, value: Float) = putData(floatPreferencesKey(key), value)
    suspend fun putDouble(key: String, value: Double) = putData(doublePreferencesKey(key), value)
    suspend fun putBoolean(key: String, value: Boolean) = putData(booleanPreferencesKey(key), value)

    fun putStringSync(key: String, value: String) = runBlocking { putString(key, value) }
    fun putIntSync(key: String, value: Int) = runBlocking { putInt(key, value) }
    fun putLongSync(key: String, value: Long) = runBlocking { putLong(key, value) }
    fun putFloatSync(key: String, value: Float) = runBlocking { putFloat(key, value) }
    fun putDoubleSync(key: String, value: Double) = runBlocking { putDouble(key, value) }
    fun putBooleanSync(key: String, value: Boolean) = runBlocking { putBoolean(key, value) }

    @CheckResult
    suspend fun getString(key: String, default: String = ""): String = getStringFlow(key, default).first()
    @CheckResult
    suspend fun getInt(key: String, default: Int = 0): Int = getIntFlow(key, default).first()
    @CheckResult
    suspend fun getLong(key: String, default: Long = 0): Long = getLongFlow(key, default).first()
    @CheckResult
    suspend fun getFloat(key: String, default: Float = 0f): Float = getFloatFlow(key, default).first()
    @CheckResult
    suspend fun getDouble(key: String, default: Double = 0.0): Double = getDoubleFlow(key, default).first()
    @CheckResult
    suspend fun getBoolean(key: String, default: Boolean = false): Boolean = getBooleanFlow(key, default).first()

    @CheckResult
    fun getStringSync(key: String, default: String = ""): String = runBlocking { getString(key, default) }
    @CheckResult
    fun getIntSync(key: String, default: Int = 0): Int = runBlocking { getInt(key, default) }
    @CheckResult
    fun getLongSync(key: String, default: Long = 0): Long = runBlocking { getLong(key, default) }
    @CheckResult
    fun getFloatSync(key: String, default: Float = 0f): Float = runBlocking { getFloat(key, default) }
    @CheckResult
    fun getDoubleSync(key: String, default: Double = 0.0): Double = runBlocking { getDouble(key, default) }
    @CheckResult
    fun getBooleanSync(key: String, default: Boolean = false): Boolean = runBlocking { getBoolean(key, default) }

    @CheckResult
    fun getStringFlow(key: String, default: String = ""): Flow<String> = getDataFlow(stringPreferencesKey(key), default)
    @CheckResult
    fun getIntFlow(key: String, default: Int = 0): Flow<Int> = getDataFlow(intPreferencesKey(key), default)
    @CheckResult
    fun getLongFlow(key: String, default: Long = 0): Flow<Long> = getDataFlow(longPreferencesKey(key), default)
    @CheckResult
    fun getFloatFlow(key: String, default: Float = 0f): Flow<Float> = getDataFlow(floatPreferencesKey(key), default)
    @CheckResult
    fun getDoubleFlow(key: String, default: Double = 0.0): Flow<Double> = getDataFlow(doublePreferencesKey(key), default)
    @CheckResult
    fun getBooleanFlow(key: String, default: Boolean = false): Flow<Boolean> = getDataFlow(booleanPreferencesKey(key), default)

    /**
     * Checks whether the DataStore contains a value for the given key name.
     *
     * Note: The key type does not matter when checking existence.
     * As long as the name matches, this will return true if a value is stored.
     *
     * @param name The name of the key to check.
     * @return True if the key exists in the DataStore, false otherwise.
     */
    suspend fun contains(name: String): Boolean {
        return dataStore.data.first().contains(stringPreferencesKey(name))
    }

    /**
     * Removes the entry with the given key name from the DataStore.
     *
     * Note: The key type does not matter when removing.
     * As long as the name matches, the corresponding entry will be removed
     * regardless of the type it was originally stored with.
     *
     * @param name The name of the key to remove.
     */
    suspend fun remove(name: String) {
        dataStore.edit { it.remove(stringPreferencesKey(name)) }
    }

    private suspend fun <T> putData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { it[key] = value }
    }

    private fun <T> getDataFlow(key: Preferences.Key<T>, default: T): Flow<T> {
        return dataStore.data.map { it[key] ?: default }
    }

    companion object {
        private const val VERSION = 1
        private val masterStore: DataStore<Preferences> by lazy {
            PreferenceDataStoreFactory.create {
                File(ApplicationHolder.app.filesDir, "appdata/master.preferences_pb")
            }
        }
        private val instances = HashMap<String, AppData>()
        val default: AppData by lazy { create("default") }

        @Synchronized
        fun create(storeName: String): AppData {
            if (!isValidFolderName(storeName)) {
                throw IllegalArgumentException("Invalid name: $storeName")
            }
            return instances.getOrPut(storeName) {
                val instance = AppData(storeName)
                runBlocking {
                    masterStore.edit {
                        it[intPreferencesKey("version")] = VERSION
                        val tables: MutableSet<String> = it[stringSetPreferencesKey("tables")]?.toMutableSet() ?: mutableSetOf()
                        tables.add(storeName)
                        it[stringSetPreferencesKey("tables")] = tables
                    }
                    instance.configStore.edit {
                        it[intPreferencesKey("version")] = VERSION
                    }
                }
                instance
            }
        }

        private fun isValidFolderName(name: String): Boolean {
            // 1. Check if the name is empty or exceeds the length limit
            if (name.isBlank() || name.length > 255) {
                return false
            }
            // 2. Check for illegal characters
            val illegalChars = listOf('<', '>', ':', '"', '/', '\\', '|', '?', '*')
            if (name.any { it in illegalChars }) {
                return false
            }
            // 3. Check if the name contains invisible characters (e.g., control characters)
            if (name.any { it.isISOControl() }) {
                return false
            }
            // 4. Check if the name ends with illegal characters (such as "." or " ")
            if (name.endsWith(".") || name.endsWith(" ")) {
                return false
            }
            return true
        }
    }
}