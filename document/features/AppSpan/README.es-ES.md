Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppSpan

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSpan.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppSpan` es una clase utilitaria que extiende `SpannableStringBuilder` para facilitar la creación de texto enriquecido en Android. Ofrece métodos prácticos para aplicar estilos como color, tamaño, fondo y más.

> Ideal para etiquetas resaltadas, títulos personalizados y textos compuestos con estilos.

## Cómo usarlo

### 1. Añadir texto con estilos

Agregar texto con uno o más efectos de span (color, fondo, tamaño, etc.):

```kotlin
val span = AppSpan()
    .text("Hola ", ForegroundColorSpan(Color.RED))
    .text("Mundo", BackgroundColorSpan(Color.YELLOW))
```

### 2. Usar métodos abreviados

#### Texto con color:

```kotlin
val span = AppSpan.textColor("Hola", Color.BLUE)
```

#### Texto con tamaño absoluto (en SP):

```kotlin
val span = AppSpan.textSize("Texto Grande", 20f)
```

#### Texto con escala relativa:

```kotlin
val span = AppSpan.textScale("Texto Escalado", 1.5f)
```

#### Texto con fondo de color:

```kotlin
val span = AppSpan.backgroundColor("Resaltado", Color.LTGRAY)
```

### 3. Encadenar métodos

Puedes combinar múltiples estilos:

```kotlin
val span = AppSpan()
    .textColor("Rojo", Color.RED)
    .textSize(" Grande", 18f)
    .backgroundColor(" Fondo", Color.YELLOW)
```

## Notas

- `AppSpan` puede usarse en cualquier lugar que acepte `CharSequence` (como TextView, Toast o Snackbar).
- Los efectos utilizan clases estándar de span de Android.