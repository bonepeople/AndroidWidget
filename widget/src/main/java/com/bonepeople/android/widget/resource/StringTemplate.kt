package com.bonepeople.android.widget.resource

/**
 * 字符串模板
 */
interface StringTemplate {
    /**
     *  字符串模板类型，用于区分不同的字符串模板，实现类需要复写此方法
     *  + 使用类型而不用字符串，可以避免类型重复导致字符串实例被覆盖，也可以避免字符串的拼写错误
     */
    val templateClass: Class<out StringTemplate>

    /**
     * 字符串模版的伴生对象
     * + 此为示例代码，开发者定义字符串模版时可参考此写法，方便在获取字符串对象时传递类型
     */
    @Suppress("unused")
    companion object {
        val templateClass: Class<StringTemplate> = StringTemplate::class.java
    }
}