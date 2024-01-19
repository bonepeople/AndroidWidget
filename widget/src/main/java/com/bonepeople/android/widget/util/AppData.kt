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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File

@Suppress("unused")
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

    suspend fun putString(key: String, value: String) {
        putData(stringPreferencesKey(key), value)
    }

    suspend fun putInt(key: String, value: Int) {
        putData(intPreferencesKey(key), value)
    }

    suspend fun putLong(key: String, value: Long) {
        putData(longPreferencesKey(key), value)
    }

    suspend fun putFloat(key: String, value: Float) {
        putData(floatPreferencesKey(key), value)
    }

    suspend fun putDouble(key: String, value: Double) {
        putData(doublePreferencesKey(key), value)
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        putData(booleanPreferencesKey(key), value)
    }

    suspend fun getString(key: String, default: String = ""): String {
        return getData(stringPreferencesKey(key), default)
    }

    suspend fun getInt(key: String, default: Int = 0): Int {
        return getData(intPreferencesKey(key), default)
    }

    suspend fun getLong(key: String, default: Long = 0): Long {
        return getData(longPreferencesKey(key), default)
    }

    suspend fun getFloat(key: String, default: Float = 0f): Float {
        return getData(floatPreferencesKey(key), default)
    }

    suspend fun getDouble(key: String, default: Double = 0.0): Double {
        return getData(doublePreferencesKey(key), default)
    }

    suspend fun getBoolean(key: String, default: Boolean = false): Boolean {
        return getData(booleanPreferencesKey(key), default)
    }

    suspend fun remove(key: String) {
        dataStore.edit { it.remove(stringPreferencesKey(key)) }
    }

    private suspend fun <T> putData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { it[key] = value }
    }

    private suspend fun <T> getData(key: Preferences.Key<T>, default: T): T {
        return dataStore.data.first()[key] ?: default
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