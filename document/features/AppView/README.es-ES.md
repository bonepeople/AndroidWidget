Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppView

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppView.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppView` es una clase de utilidades para componentes `View` en Android.  
Proporciona funciones como prevención de clics múltiples, gestión de visibilidad, almacenamiento de datos personalizados y herramientas de medición.

## Funciones clave

### 1. Prevenir clics rápidos consecutivos

```kotlin
boton.singleClick {
    // Acción al hacer clic seguro
}
```

Con intervalo personalizado:

```kotlin
boton.singleClick(1000L) {
    // Intervalo de 1 segundo
}
```

### 2. Control de visibilidad

```kotlin
vista.show()       // VISIBLE
vista.hide()       // INVISIBLE
vista.gone()       // GONE
vista.switchShow(true)     // VISIBLE si es true, GONE si es false
vista.switchVisible(false) // INVISIBLE si es false
```

### 3. Almacenamiento personalizado por vista

```kotlin
vista.putExtra("clave", 123)
valor: Int = vista.getExtra("clave", 0)
vista.removeExtra("clave")
```

### 4. Medición personalizada para vistas

```kotlin
val param = AppView.resolveMeasureParameter(this, widthMeasureSpec, heightMeasureSpec)
val maxWidth = param.maxWidth
val heightMode = param.heightModeName
```

Muy útil para `onMeasure()` en vistas personalizadas.