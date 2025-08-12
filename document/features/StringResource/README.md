Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# String Resource Manager

## Introduction

This utility provides a flexible, template-based approach to managing localized string resources in Kotlin projects. It is suitable for Android applications that require modular and multilingual support.

Core components:

- `StringTemplate`: interface for defining string template types
- `StringResourceManager`: registry for localized string instances

## Use Cases

- Multi-module or multi-feature projects with centralized string management
- Programmatic localization outside of standard `strings.xml` workflows

## Usage

### Create a template

Define an abstract class extending `StringTemplate`. Add abstract properties based on the strings your module requires.

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

You can define more or fewer abstract properties depending on your UI needs.

### Implement language-specific classes

Each implementation corresponds to a locale and overrides the abstract string properties.

```kotlin
class ChatTextEnUS : ChatText() {
    override val send = "Send"
    override val fileSizeToast = "The image size exceeds 5MB"
    override val noImageToast = "No image selected"
    override val errorToast = "Something went wrong, please try again"
}
```

### Use strings in code

```kotlin
val text: ChatText = StringResourceManager.get(ChatText.templateClass)
views.textViewSend.text = text.send
```

## Source Code

- [StringResourceManager.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringResourceManager.kt)
- [StringTemplate.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringTemplate.kt)