Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Gestor de Recursos de Texto - Guía de Uso

> **Nota**: Este documento fue generado con la ayuda de ChatGPT.

## Visión General

Esta herramienta proporciona una forma organizada y flexible de gestionar textos localizados en proyectos Kotlin. Ideal para aplicaciones Android con soporte multilingüe y modularidad.

Incluye:

- `StringTemplate`: Interfaz para definir plantillas de texto.
- `StringResourceManager`: Registra y recupera textos según el idioma.

## Cómo Usarlo

### 1. Crear una Plantilla

Define una clase abstracta que herede de `StringTemplate`. Puedes declarar las propiedades abstractas necesarias para tu interfaz.

```kotlin
abstract class ChatText : StringTemplate {
    override val templateClass: Class<out StringTemplate> = Companion.templateClass

    abstract val send: String
    abstract val fileSizeToast: String
    abstract val noImageToast: String
    abstract val errorToast: String

    companion object {
        val templateClass: Class<ChatText> = ChatText::class.java

        // Recomendado: registrar implementaciones en el bloque init
        init {
            StringResourceManager.register(ChatTextEnUS(), Locale.ENGLISH)
            StringResourceManager.register(ChatTextZhCN(), Locale.SIMPLIFIED_CHINESE)
            StringResourceManager.register(ChatTextEsES(), Locale("es", "ES"))
        }
    }
}
```

> 💡 Puedes agregar o quitar propiedades según lo requiera tu interfaz de usuario.

### 2. Crear Implementaciones por Idioma

Cada clase representa un idioma y sobrescribe los valores definidos.

```kotlin
class ChatTextEnUS : ChatText() {
    override val send = "Send"
    override val fileSizeToast = "The image size exceeds 5MB"
    override val noImageToast = "No image selected"
    override val errorToast = "Something went wrong, please try again"
}
```

### 3. Obtener los Textos en Código

```kotlin
val text: ChatText = StringResourceManager.get(ChatText.templateClass)
views.textViewSend.text = text.send
```

## Código Fuente

- [`StringResourceManager.kt`](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringResourceManager.kt)
- [`StringTemplate.kt`](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringTemplate.kt)
