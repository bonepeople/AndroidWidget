多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# String Resource Manager

## 简介

该工具提供灵活、模板化的多语言字符串资源管理方式，适用于需要模块化和多语言支持的 Kotlin / Android 项目。

核心组件：

- `StringTemplate`：定义字符串模板类型的接口
- `StringResourceManager`：本地化字符串实例的注册表

## 场景

- 多模块或多功能项目的集中字符串管理
- 在标准 `strings.xml` 流程之外的程序化本地化

## 使用方式

### 创建模板

定义继承 `StringTemplate` 的抽象类，按模块需求添加抽象属性。

```kotlin
abstract class ChatText : StringTemplate {
    override val templateClass: Class<out StringTemplate> = Companion.templateClass

    abstract val send: String
    abstract val fileSizeToast: String
    abstract val noImageToast: String
    abstract val errorToast: String

    companion object {
        val templateClass: Class<ChatText> = ChatText::class.java

        // 建议在此注册各语言实现
        init {
            StringResourceManager.register(ChatTextEnUS(), Locale.ENGLISH)
            StringResourceManager.register(ChatTextZhCN(), Locale.SIMPLIFIED_CHINESE)
            StringResourceManager.register(ChatTextEsES(), Locale("es", "ES"))
        }
    }
}
```

可按 UI 需求增减抽象属性数量。

### 实现各语言类

每个实现对应一个 locale，覆盖抽象字符串属性。

```kotlin
class ChatTextEnUS : ChatText() {
    override val send = "Send"
    override val fileSizeToast = "The image size exceeds 5MB"
    override val noImageToast = "No image selected"
    override val errorToast = "Something went wrong, please try again"
}
```

### 在代码中使用

```kotlin
val text: ChatText = StringResourceManager.get(ChatText.templateClass)
views.textViewSend.text = text.send
```

## 源码链接

- [StringResourceManager.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringResourceManager.kt)
- [StringTemplate.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringTemplate.kt)