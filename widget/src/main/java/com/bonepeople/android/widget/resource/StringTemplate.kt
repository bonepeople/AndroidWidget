package com.bonepeople.android.widget.resource

/**
 * 字符串模板
 */
interface StringTemplate {
    /**
     *  字符串模板类型，用于区分不同的字符串模板，实现类需要复写此方法
     */
    val templateClass: Class<out StringTemplate>

    @Suppress("unused")
    companion object {
        /**
         * 字符串模板类型
         * + 在伴生对象中定义，方便用户直接获取类型
         */
        val templateClass: Class<StringTemplate> = StringTemplate::class.java
    }
}