Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppDensity

## Introducción

`AppDensity` proporciona métodos de conversión de densidad de píxeles para Android, incluidas conversiones entre unidades px, dp y sp.

## Características

- Convertir dp / sp a px
- Convertir px a dp / sp
- Soporte para `DisplayMetrics` personalizados o determinados automáticamente

## Uso

```kotlin
val px = AppDensity.getPx(16f) // 16dp -> px
val dp = AppDensity.getDp(32)  // px -> dp
val sp = AppDensity.getSp(24)  // px -> sp
```

Especificar un `DisplayMetrics` personalizado:

```kotlin
val customMetrics = Resources.getSystem().displayMetrics
val px = AppDensity.getPx(16f, TypedValue.COMPLEX_UNIT_SP, customMetrics)
```

## Código fuente

[AppDensity.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppDensity.kt)