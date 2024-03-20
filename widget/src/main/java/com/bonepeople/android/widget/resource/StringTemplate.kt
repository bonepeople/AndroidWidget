package com.bonepeople.android.widget.resource

/**
 * String Template
 */
interface StringTemplate {
    /**
     *  The type of the string template, used to distinguish between different string templates.
     *  Implementing classes must override this property.
     *  + Using a type instead of a string avoids issues such as type conflicts leading to overwritten instances or spelling errors in string identifiers.
     */
    val templateClass: Class<out StringTemplate>

    /**
     * Companion object for the string template.
     * + This is an example implementation. Developers can reference this structure when defining string templates.
     *   It provides a convenient way to pass the template type when retrieving string instances.
     */
    @Suppress("unused")
    companion object {
        val templateClass: Class<StringTemplate> = StringTemplate::class.java
    }
}