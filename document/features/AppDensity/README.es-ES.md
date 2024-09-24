Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Utilidad AppDensity

Esta utilidad proporciona métodos comunes para la conversión de densidad de píxeles en Android, incluyendo conversiones entre px, dp y sp.

## Características

* Convertir dp / sp a px
* Convertir px a dp / sp
* Soporta especificar o detectar automáticamente `DisplayMetrics`

## Uso

```kotlin
val px = AppDensity.getPx(16f) // 16dp -> px
val dp = AppDensity.getDp(32)  // px -> dp
val sp = AppDensity.getSp(24)  // px -> sp
```

También puedes especificar tu propio `DisplayMetrics`:

```kotlin
val customMetrics = Resources.getSystem().displayMetrics
val px = AppDensity.getPx(16f, TypedValue.COMPLEX_UNIT_SP, customMetrics)
```

## Código fuente

[Ver código fuente](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppDensity.kt)

---

Este documento fue generado con la ayuda de ChatGPT.