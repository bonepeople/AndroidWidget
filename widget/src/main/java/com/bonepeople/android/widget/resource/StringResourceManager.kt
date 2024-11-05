package com.bonepeople.android.widget.resource

import java.util.Locale

/**
 * String Resource Manager
 *
 * + Usage:
 * 1. Define a string template by inheriting from [StringTemplate] and implementing the [StringTemplate.templateClass] property.
 *    - Templates can be abstract classes or interfaces.
 *    - Different modules or pages can define distinct string templates, specifying variables to be implemented by subclasses.
 * 2. Define string instances by inheriting from a string template and implementing specific string content.
 *    - A single template can have multiple language-specific string instances.
 * 3. Before using a string, register string instances using the [register] method.
 * 4. Retrieve string instances in your code by calling the [get] method.
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/StringResource)
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
object StringResourceManager {
    private val localStrings = HashMap<String, LinkedHashMap<String, LinkedHashMap<String, StringTemplate>>>()

    /**
     * Registers string instances.
     * + The order of registration determines the default string to display when no match is found.
     * @param strings The string instance to register.
     * @param locales One or more locales. The string instance will be registered for each specified locale (e.g., `Locale.US`).
     */
    fun register(strings: StringTemplate, vararg locales: Locale) {
        locales.forEach { locale: Locale ->
            // Retrieve the class name of the string template to create a corresponding container
            val className: String = strings.templateClass.name
            // Get or create a language container for the template class
            val languageMap: LinkedHashMap<String, LinkedHashMap<String, StringTemplate>> = localStrings.getOrPut(className) { LinkedHashMap() }
            // Get or create a country-specific container for the language
            val countryMap: LinkedHashMap<String, StringTemplate> = languageMap.getOrPut(locale.language) { LinkedHashMap() }
            // Register the string instance in the country container
            countryMap[locale.country] = strings
        }
    }

    /**
     * Retrieves the string instance for a specified locale.
     * + If no match is found for the specified locale, the first registered string instance is returned.
     * @param templateClass The string template type (e.g., `StringTemplate::class.java`).
     * @param locale The locale to retrieve the string instance for (default is the system's default locale, e.g., `Locale.US`).
     * @return The string instance matching the given locale or a fallback instance if no exact match is found.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : StringTemplate> get(templateClass: Class<T>, locale: Locale = Locale.getDefault()): T {
        require(localStrings.isNotEmpty()) { "No string instances have been registered. Please register instances before use." }
        // Retrieve the class name of the string template to query the corresponding container
        val className: String = templateClass.name
        // Query the language container for the specified template class
        val languageMap: LinkedHashMap<String, LinkedHashMap<String, StringTemplate>> = localStrings.getOrPut(className) { LinkedHashMap() }
        require(languageMap.isNotEmpty()) { "No languages have been registered. Please register languages before use." }
        // Query the country container for the specified language
        val countryMap: LinkedHashMap<String, StringTemplate> = languageMap.getOrPut(locale.language) { LinkedHashMap() }
        // Retrieve the string instance for the specified country
        val strings: StringTemplate? = countryMap[locale.country]
        return strings as? T ?: if (countryMap.isEmpty()) {
            // If no match is found for the language dimension, return the first registered string instance
            languageMap.values.first().values.first() as T
        } else {
            // If no match is found for the country dimension, return a string instance from the same language
            countryMap.values.first() as T
        }
    }
}