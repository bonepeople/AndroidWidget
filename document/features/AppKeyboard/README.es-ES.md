Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppKeyboard

> Este documento fue elaborado con la ayuda de ChatGPT  
> Enlace al código fuente: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppKeyboard.kt

## Introducción

`AppKeyboard` es una clase utilitaria para gestionar el teclado virtual en aplicaciones Android. Permite mostrar u ocultar el teclado de manera programada y detectar si se debe cerrar el teclado según la interacción del usuario.

## Características

- Detecta si se debe ocultar el teclado según el evento táctil;
- Muestra el teclado virtual;
- Oculta el teclado virtual.

## Cómo usar

### 1. Determinar si se debe ocultar el teclado

Úsalo dentro de `dispatchTouchEvent` para ocultar el teclado cuando el usuario toque fuera de un campo de texto:

```kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (AppKeyboard.needHideKeyboard(currentFocus, ev)) {
        AppKeyboard.hideKeyboard()
    }
    return super.dispatchTouchEvent(ev)
}
```

### 2. Mostrar el teclado virtual

```kotlin
AppKeyboard.showKeyboard(myEditText)
```

### 3. Ocultar el teclado virtual

```kotlin
AppKeyboard.hideKeyboard()
```

## Casos recomendados de uso

- Ocultar automáticamente el teclado al tocar fuera de un EditText;
- Mostrar el teclado cuando un campo necesita recibir foco programáticamente;
- Gestionar el comportamiento del teclado de manera centralizada.

## Notas

- `showKeyboard()` utiliza un retraso para garantizar que la vista esté lista antes de solicitar el foco.