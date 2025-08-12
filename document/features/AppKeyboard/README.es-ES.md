Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppKeyboard

## Introducción

`AppKeyboard` gestiona el teclado virtual en aplicaciones Android. Proporciona métodos para mostrar u ocultar el teclado y detectar si un evento táctil debe provocar su cierre.

## Casos de uso

- Ocultar automáticamente el teclado al tocar fuera de un `EditText`
- Mostrar el teclado cuando un campo de entrada recibe foco programáticamente
- Centralizar el comportamiento del teclado en la app

## Características

- Detectar si el teclado debe ocultarse según la entrada táctil
- Mostrar el teclado virtual programáticamente
- Ocultar el teclado virtual

## Uso

Ocultar el teclado al tocar fuera del campo — usar en `Activity.dispatchTouchEvent`:

```kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (AppKeyboard.needHideKeyboard(currentFocus, ev)) {
        AppKeyboard.hideKeyboard()
    }
    return super.dispatchTouchEvent(ev)
}
```

Mostrar el teclado virtual:

```kotlin
AppKeyboard.showKeyboard(myEditText)
```

Ocultar el teclado virtual:

```kotlin
AppKeyboard.hideKeyboard()
```

## Notas

- `showKeyboard()` usa un breve retraso para asegurar que la vista esté lista antes de solicitar foco y mostrar el teclado.

## Código fuente

[AppKeyboard.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppKeyboard.kt)