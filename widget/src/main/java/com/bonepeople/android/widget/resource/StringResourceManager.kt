package com.bonepeople.android.widget.resource

import java.util.Locale

/**
 * 字符串资源管理器
 */
@Suppress("unused")
object StringResourceManager {
    private val localStrings = HashMap<String, LinkedHashMap<String, LinkedHashMap<String, StringTemplate>>>()

    /**
     * 注册字符串实例
     * + 以先后顺序关系确定默认展示的字符串，在未找到匹配的字符串时优先展示先注册的字符串
     * @param strings 字符串实例
     * @param locales 语言环境，可传入多个语言环境，将同一字符串实例注册到多个语言环境中
     */
    fun register(strings: StringTemplate, vararg locales: Locale) {
        locales.forEach { locale: Locale ->
            //获取字符串模版的类名，用于创建对应的容器
            val className: String = strings.templateClass.name
            //根据类名获取对应的语言容器
            val languageMap: LinkedHashMap<String, LinkedHashMap<String, StringTemplate>> = localStrings.getOrPut(className) { LinkedHashMap() }
            //根据语言获取对应的国家容器
            val countryMap: LinkedHashMap<String, StringTemplate> = languageMap.getOrPut(locale.language) { LinkedHashMap() }
            //将字符串实例注册到国家容器中
            countryMap[locale.country] = strings
        }
    }

    /**
     * 获取指定语言环境对应的字符串实例
     * + 未找到指定语言环境的字符串实例时，将返回注册的第一个语言环境的第一个字符串实例
     * @param templateClass 字符串模版类型
     * @param locale 语言环境，默认为系统语言环境
     */
    @Suppress("unchecked_cast")
    fun <T : StringTemplate> get(templateClass: Class<T>, locale: Locale = Locale.getDefault()): T {
        require(localStrings.isNotEmpty()) { "未注册任何字符串实例，请注册后再使用" }
        //获取字符串模版的类名，用于查询对应的容器
        val className: String = templateClass.name
        //查询类名对应的语言容器
        val languageMap: LinkedHashMap<String, LinkedHashMap<String, StringTemplate>> = localStrings.getOrPut(className) { LinkedHashMap() }
        require(languageMap.isNotEmpty()) { "未注册任何语言，请注册后再使用" }
        //查询语言对应的国家容器
        val countryMap: LinkedHashMap<String, StringTemplate> = languageMap.getOrPut(locale.language) { LinkedHashMap() }
        //查询国家对应的字符串实例
        val strings: StringTemplate? = countryMap[locale.country]
        return strings as? T ?: if (countryMap.isEmpty()) { //语言维度未找到，返回注册的第一个语言的第一个字符串实例
            languageMap.values.first().values.first() as T
        } else { //国家维度未找到，返回同语言字符串实例
            countryMap.values.first() as T
        }
    }
}