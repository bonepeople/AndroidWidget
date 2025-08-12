Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# String Resource Manager

## Introducción

Esta utilidad proporciona un enfoque flexible y basado en plantillas para gestionar recursos de cadenas localizados en proyectos Kotlin. Es adecuada para aplicaciones Android que requieren soporte modular y multilingüe.

Componentes principales:

- `StringTemplate`: interfaz para definir tipos de plantillas de cadenas
- `StringResourceManager`: registro de instancias de cadenas localizadas

## Casos de uso

- Gestión centralizada de cadenas en proyectos con múltiples módulos o funcionalidades
- Localización programática fuera del flujo estándar de `strings.xml`

## Uso

### Crear una plantilla

Define una clase abstracta que extienda `StringTemplate`. Añade propiedades abstractas según las cadenas que requiera tu módulo.

```kotlin
abstract class ChatText : StringTemplate {
    override val templateClass: Class<out StringTemplate> = Companion.templateClass

    abstract val send: String
    abstract val fileSizeToast: String
    abstract val noImageToast: String
    abstract val errorToast: String

    companion object {
        val templateClass: Class<ChatText> = ChatText::class.java

        // Recomendado: registrar implementaciones aquí
        init {
            StringResourceManager.register(ChatTextEnUS(), Locale.ENGLISH)
            StringResourceManager.register(ChatTextZhCN(), Locale.SIMPLIFIED_CHINESE)
            StringResourceManager.register(ChatTextEsES(), Locale("es", "ES"))
        }
    }
}
```

Puedes definir más o menos propiedades abstractas según tus necesidades de UI.

### Implementar clases por idioma

Cada implementación corresponde a un locale y debe sobrescribir las propiedades abstractas de cadenas.

```kotlin
class ChatTextEnUS : ChatText() {
    override val send = "Send"
    override val fileSizeToast = "The image size exceeds 5MB"
    override val noImageToast = "No image selected"
    override val errorToast = "Something went wrong, please try again"
}
```

### Usar cadenas en código

```kotlin
val text: ChatText = StringResourceManager.get(ChatText.templateClass)
views.textViewSend.text = text.send
```

## Código fuente

- [StringResourceManager.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringResourceManager.kt)
- [StringTemplate.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/resource/StringTemplate.kt)