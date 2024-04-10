package com.bonepeople.android.widget.resource

import java.util.Locale

@Suppress("unused", "MemberVisibilityCanBePrivate")
object StringResourceManager {
    private val localStrings = HashMap<String, LinkedHashMap<String, LinkedHashMap<String, StringTemplate>>>()

    fun register(strings: StringTemplate, vararg locales: Locale) {
        locales.forEach { locale: Locale ->
            val className: String = strings.templateClass.name
            val languageMap: LinkedHashMap<String, LinkedHashMap<String, StringTemplate>> = localStrings.getOrPut(className) { LinkedHashMap() }
            val countryMap: LinkedHashMap<String, StringTemplate> = languageMap.getOrPut(locale.language) { LinkedHashMap() }
            countryMap[locale.country] = strings
        }
    }

    @Suppress("unchecked_cast")
    fun <T : StringTemplate> get(templateClass: Class<T>, locale: Locale = Locale.getDefault()): T {
        require(localStrings.isNotEmpty()) { "localStrings is empty" }
        val className: String = templateClass.name
        val languageMap: LinkedHashMap<String, LinkedHashMap<String, StringTemplate>> = localStrings.getOrPut(className) { LinkedHashMap() }
        require(languageMap.isNotEmpty()) { "languageMap is empty" }
        val countryMap: LinkedHashMap<String, StringTemplate> = languageMap.getOrPut(locale.language) { LinkedHashMap() }
        val strings: StringTemplate? = countryMap[locale.country]
        return strings as? T ?: if (countryMap.isEmpty()) {
            languageMap.values.first().values.first() as T
        } else {
            countryMap.values.first() as T
        }
    }
}