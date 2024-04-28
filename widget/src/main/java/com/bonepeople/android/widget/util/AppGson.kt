package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import com.google.gson.*
import com.google.gson.internal.bind.TreeTypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Utility class for JSON serialization and deserialization using Gson.
 */
@Suppress("Unused")
object AppGson {
    /**
     * Default Gson instance with custom configuration:
     * - Disables HTML escaping.
     * - Includes a custom adapter to filter out null values during deserialization.
     */
    val defaultGson: Gson by lazy { addNotNullAdapter(GsonBuilder().disableHtmlEscaping().create()) }

    /**
     * Converts an object into a JSON string.
     * @param data The object to convert.
     * @param gson An optional custom [Gson] instance; defaults to [defaultGson].
     * @return JSON representation of the object.
     */
    @CheckResult
    @JvmOverloads
    fun toJson(data: Any?, gson: Gson = defaultGson): String {
        return gson.toJson(data)
    }

    /**
     * Converts a JSON string into an object of the specified type.
     * @param json The JSON string to parse.
     * @param gson An optional custom [Gson] instance; defaults to [defaultGson].
     * @throws JsonSyntaxException if the JSON string has invalid syntax.
     * @throws IllegalStateException if the JSON string is null or empty.
     */
    @CheckResult
    @JvmOverloads
    inline fun <reified R> toObject(json: String?, gson: Gson = defaultGson): R {
        return if (R::class.java.typeParameters.isNotEmpty()) {
            gson.fromJson(json, object : TypeToken<R>() {}.type) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
        } else {
            gson.fromJson(json, R::class.java) ?: throw IllegalStateException("Empty JSON string for decode '$json'")
        }
    }

    /**
     * Adds a custom adapter to a [Gson] instance to filter out null values during deserialization.
     * @param originGson The original [Gson] instance to enhance.
     * @return A new [Gson] instance with the null-removal adapter.
     */
    @CheckResult
    fun addNotNullAdapter(originGson: Gson): Gson {
        return originGson.newBuilder().registerTypeAdapterFactory(GsonFactory(originGson)).create()
    }

    /**
     * Factory for creating Gson adapters that handle null removal.
     */
    private class GsonFactory(private val originGson: Gson) : TypeAdapterFactory {
        private val adapter = GsonAdapter()
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
            return TreeTypeAdapter.newFactoryWithMatchRawType(type, adapter).create(originGson, type)
        }
    }

    /**
     * Adapter for handling serialization and deserialization.
     * - During deserialization, removes null values from objects and arrays.
     */
    private class GsonAdapter : JsonDeserializer<Any>, JsonSerializer<Any> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any {
            if (json.isJsonObject) searchInObject(json.asJsonObject)
            if (json.isJsonArray) searchInArray(json.asJsonArray)
            return context.deserialize(json, typeOfT)
        }

        private fun searchInObject(obj: JsonObject) {
            val nullKey = LinkedList<String>()
            obj.keySet().forEach {
                val element: JsonElement = obj.get(it)
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
            arr.forEachIndexed { index, element ->
                when {
                    element.isJsonNull -> nullIndex.addFirst(index)
                    element.isJsonObject -> searchInObject(element.asJsonObject)
                    element.isJsonArray -> searchInArray(element.asJsonArray)
                }
            }
            nullIndex.forEach { arr.remove(it) }
        }

        override fun serialize(src: Any, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return context.serialize(src, typeOfSrc)
        }
    }
}