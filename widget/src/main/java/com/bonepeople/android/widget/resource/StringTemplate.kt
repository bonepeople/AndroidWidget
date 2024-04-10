package com.bonepeople.android.widget.resource

interface StringTemplate {
    val templateClass: Class<out StringTemplate>

    @Suppress("unused")
    companion object {
        val templateClass: Class<StringTemplate> = StringTemplate::class.java
    }
}