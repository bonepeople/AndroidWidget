Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de Uso de IntentLauncher

Este documento fue generado con la ayuda de ChatGPT.

## Introducción

`IntentLauncher` es una utilidad que simplifica el uso de `startActivityForResult` en Android. Permite lanzar un `Intent` mediante la extensión `.launch()` y manejar el resultado de forma encadenada y reactiva.

## Cómo usarlo

### 1. Configuración

No es necesario registrar manualmente ningún callback. El registro del ciclo de vida se maneja automáticamente a través del módulo interno.

### 2. Lanzar un `Activity` y manejar el resultado

Puedes lanzar un `Intent` directamente usando `.launch()` y encadenar los manejadores:

```kotlin
val intent = Intent(this, DetailActivity::class.java)

intent.launch()
    .onSuccess { data ->
        // Resultado exitoso (Intent?)
    }
    .onFailure { result ->
        // Resultado diferente a RESULT_OK (ActivityResult)
    }
    .onResult { result ->
        // Manejo general del resultado
    }
```

## Concurrencia (configuración opcional)

Por defecto, cada `Activity` crea una instancia de `IntentLauncher`, lo cual es suficiente en la mayoría de los casos.

Si ves una excepción como:

```
IllegalStateException: The number of simultaneously used IntentLaunchers exceeds the limit.
```

Puedes aumentar la capacidad así:

```kotlin
IntentLauncher.initialCapacity = 2 // o más
```

## Notas

- Si usas modos de lanzamiento como `singleTop`, `singleTask` o `singleInstance`, añade manualmente los flags necesarios al `Intent`.
- Solo necesitas ajustar `initialCapacity` si vas a lanzar múltiples actividades al mismo tiempo y necesitas resultados simultáneos.

## Enlaces al código fuente

- [IntentExtensions.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/ActivityResultContracts.kt)
- [IntentLauncher.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentLauncher.kt)
- [IntentResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentResult.kt)
