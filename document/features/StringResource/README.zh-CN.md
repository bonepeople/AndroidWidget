多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# 字符串资源管理器 - 使用说明

> **说明**：本文档由 ChatGPT 协助完成。

## 概述

该工具类为 Kotlin 项目提供了灵活的多语言字符串管理机制，适用于需要模块化或国际化的 Android 应用开发。

主要包括：

- `StringTemplate`：用于定义字符串模板结构的接口。
- `StringResourceManager`：用于注册与获取具体语言的字符串实现。

## 使用方法

### 1. 定义字符串模板

创建一个抽象类实现 `StringTemplate`，根据需要添加抽象属性。

```kotlin
abstract class ChatText : StringTemplate {
    override val templateClass: Class<out StringTemplate> = Companion.templateClass

    abstract val send: String
    abstract val fileSizeToast: String
    abstract val noImageToast: String
    abstract val errorToast: String

    companion object {
        val templateClass: Class<ChatText> = ChatText::class.java

        // 建议在这里注册所有实现类，初始化一次即可
        init {
            StringResourceManager.register(ChatTextEnUS(), Locale.ENGLISH)
            StringResourceManager.register(ChatTextZhCN(), Locale.SIMPLIFIED_CHINESE)
            StringResourceManager.register(ChatTextEsES(), Locale("es", "ES"))
        }
    }
}
```

> 💡 抽象属性可根据实际使用需求自由添加。

### 2. 定义语言实现类

每个实现类用于具体语言，需重写所有属性。

```kotlin
class ChatTextEnUS : ChatText() {
    override val send = "Send"
    override val fileSizeToast = "The image size exceeds 5MB"
    override val noImageToast = "No image selected"
    override val errorToast = "Something went wrong, please try again"
}
```

### 3. 获取并使用字符串

```kotlin
val text: ChatText = StringResourceManager.get(ChatText.templateClass)
views.textViewSend.text = text.send
```

## 源码链接

- [`StringResourceManager.kt`](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringResourceManager.kt)
- [`StringTemplate.kt`](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringTemplate.kt)
