package com.bonepeople.android.widget.util

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

@Suppress("unused", "MemberVisibilityCanBePrivate")
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

    suspend fun getString(key: String, default: String = ""): String = getStringFlow(key, default).first()
    suspend fun getInt(key: String, default: Int = 0): Int = getIntFlow(key, default).first()
    suspend fun getLong(key: String, default: Long = 0): Long = getLongFlow(key, default).first()
    suspend fun getFloat(key: String, default: Float = 0f): Float = getFloatFlow(key, default).first()
    suspend fun getDouble(key: String, default: Double = 0.0): Double = getDoubleFlow(key, default).first()
    suspend fun getBoolean(key: String, default: Boolean = false): Boolean = getBooleanFlow(key, default).first()

    fun getStringSync(key: String, default: String = ""): String = runBlocking { getString(key, default) }
    fun getIntSync(key: String, default: Int = 0): Int = runBlocking { getInt(key, default) }
    fun getLongSync(key: String, default: Long = 0): Long = runBlocking { getLong(key, default) }
    fun getFloatSync(key: String, default: Float = 0f): Float = runBlocking { getFloat(key, default) }
    fun getDoubleSync(key: String, default: Double = 0.0): Double = runBlocking { getDouble(key, default) }
    fun getBooleanSync(key: String, default: Boolean = false): Boolean = runBlocking { getBoolean(key, default) }

    fun getStringFlow(key: String, default: String = ""): Flow<String> = getDataFlow(stringPreferencesKey(key), default)
    fun getIntFlow(key: String, default: Int = 0): Flow<Int> = getDataFlow(intPreferencesKey(key), default)
    fun getLongFlow(key: String, default: Long = 0): Flow<Long> = getDataFlow(longPreferencesKey(key), default)
    fun getFloatFlow(key: String, default: Float = 0f): Flow<Float> = getDataFlow(floatPreferencesKey(key), default)
    fun getDoubleFlow(key: String, default: Double = 0.0): Flow<Double> = getDataFlow(doublePreferencesKey(key), default)
    fun getBooleanFlow(key: String, default: Boolean = false): Flow<Boolean> = getDataFlow(booleanPreferencesKey(key), default)

    suspend fun remove(key: String) {
        dataStore.edit { it.remove(stringPreferencesKey(key)) }
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

        fun create(storeName: String): AppData {
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
    }
}