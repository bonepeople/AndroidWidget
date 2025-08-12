Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppSpan

## Introducción

`AppSpan` simplifica la creación de contenido de texto enriquecido en Android mediante spans. Extiende `SpannableStringBuilder` y proporciona métodos encadenables para estilizar texto con color, tamaño, fondo y más.

## Casos de uso

- Etiquetas resaltadas y títulos personalizados
- Párrafos con estilos compuestos en `TextView`, Toast, Snackbar, etc.

## Características

- API encadenada para añadir texto estilizado
- Helpers integrados para color de primer plano, tamaño de texto, escala relativa y color de fondo
- Compatible donde se acepte `CharSequence`

## Uso

Añadir texto con uno o más efectos span:

```kotlin
val span = AppSpan()
    .text("Hello ", ForegroundColorSpan(Color.RED))
    .text("World", BackgroundColorSpan(Color.YELLOW))
```

Métodos auxiliares integrados:

```kotlin
val colorSpan = AppSpan.textColor("Hello", Color.BLUE)
val sizeSpan = AppSpan.textSize("Large Text", 20f)
val scaleSpan = AppSpan.textScale("Scaled Text", 1.5f)
val bgSpan = AppSpan.backgroundColor("Highlighted", Color.LTGRAY)
```

Encadenar múltiples métodos de estilo:

```kotlin
val span = AppSpan()
    .textColor("Red", Color.RED)
    .textSize(" Big", 18f)
    .backgroundColor(" Highlight", Color.YELLOW)
```

## Notas

- Los efectos se aplican con las clases span estándar de Android.

## Código fuente

[AppSpan.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSpan.kt)