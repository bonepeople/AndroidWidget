Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# String Resource Manager - Usage Guide

> **Note**: This documentation was generated with assistance from ChatGPT.

## Overview

This utility provides a flexible and structured way to manage localized string resources in Kotlin-based projects. It's suitable for Android applications that require modular and multilingual support.

It includes:

- `StringTemplate`: An interface for defining string template types.
- `StringResourceManager`: A registry for localized string instances.

## How to Use

### 1. Create a Template

Define an abstract class that extends `StringTemplate`. You can freely add abstract properties based on the strings your module or feature requires.

```kotlin
abstract class ChatText : StringTemplate {
    override val templateClass: Class<out StringTemplate> = Companion.templateClass

    abstract val send: String
    abstract val fileSizeToast: String
    abstract val noImageToast: String
    abstract val errorToast: String

    companion object {
        val templateClass: Class<ChatText> = ChatText::class.java

        // Recommended: Register implementations here
        init {
            StringResourceManager.register(ChatTextEnUS(), Locale.ENGLISH)
            StringResourceManager.register(ChatTextZhCN(), Locale.SIMPLIFIED_CHINESE)
            StringResourceManager.register(ChatTextEsES(), Locale("es", "ES"))
        }
    }
}
```

> 💡 You can define more or fewer abstract properties depending on your UI needs.

### 2. Implement Language-Specific Classes

Each implementation corresponds to a locale and should override the abstract string properties.

```kotlin
class ChatTextEnUS : ChatText() {
    override val send = "Send"
    override val fileSizeToast = "The image size exceeds 5MB"
    override val noImageToast = "No image selected"
    override val errorToast = "Something went wrong, please try again"
}
```

### 3. Use the Strings in Code

```kotlin
val text: ChatText = StringResourceManager.get(ChatText.templateClass)
views.textViewSend.text = text.send
```

## Source Code

- [`StringResourceManager.kt`](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringResourceManager.kt)
- [`StringTemplate.kt`](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringTemplate.kt)
