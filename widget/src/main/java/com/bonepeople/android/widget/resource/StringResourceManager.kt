package com.bonepeople.android.widget.resource

import java.util.Locale

/**
 * 字符串资源管理器
 * + 使用方法：
 * 1. 定义字符串模版，继承自[StringTemplate]，并实现[StringTemplate.templateClass]属性，此模版可定义为抽象类或接口，可以为不同模块或页面定义不同的字符串模版，在模版中定义需要被子类实现的变量
 * 2. 定义字符串实例，继承自字符串模版，实现具体的字符串内容，同一个模版可以定义多个不同语言版本的字符串实例
 * 3. 在使用字符串之前，调用[register]方法注册字符串实例
 * 4. 在需要使用字符串的地方，调用[get]方法获取字符串实例
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object StringResourceManager {
    private val localStrings = HashMap<String, LinkedHashMap<String, LinkedHashMap<String, StringTemplate>>>()

    /**
     * 注册字符串实例
     * + 以先后顺序关系确定默认展示的字符串，在未找到匹配的字符串时优先展示先注册的字符串
     * @param strings 字符串实例
     * @param locales 语言环境，可传入多个语言环境，将同一字符串实例注册到多个语言环境中，`Locale.US`
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
     * @param templateClass 字符串模版类型，`StringTemplate::class.java`
     * @param locale 语言环境，默认为系统语言环境，`Locale.US`
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