Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# IntentLauncher

## Introducción

`IntentLauncher` simplifica el flujo de `startActivityForResult` en Android. Lanza una `Activity` con la extensión `.launch()` y gestiona el resultado mediante callbacks encadenados.

El monitoreo del ciclo de vida y el registro del launcher se gestionan automáticamente, sin configuración manual.

## Características

- Manejadores encadenados `.onSuccess`, `.onFailure` y `.onResult`
- Gestión automática del ciclo de vida
- Soporte para lanzamientos concurrentes de actividades (capacidad configurable)

## Uso

Lanzar un `Intent` y gestionar el resultado:

```kotlin
val intent = Intent(this, DetailActivity::class.java)

intent.launch()
    .onSuccess { data ->
        // Gestionar resultado exitoso (Intent?)
    }
    .onFailure { result ->
        // Gestionar resultado distinto de RESULT_OK (ActivityResult)
    }
    .onResult { result ->
        // Gestionar todos los casos
    }
```

### Lanzamientos concurrentes

Por defecto, cada Activity inicializa una instancia de `IntentLauncher`, suficiente para la mayoría de casos.

Si aparece:

```
IllegalStateException: The number of simultaneously used IntentLaunchers exceeds the limit.
```

Aumenta la capacidad:

```kotlin
IntentLauncher.initialCapacity = 2 // o más
```

## Notas

- Para modos de lanzamiento como `singleTop`, `singleTask` o `singleInstance`, añade manualmente los flags apropiados al `Intent`.
- Ajusta `initialCapacity` solo al lanzar varias actividades para resultado de forma concurrente.

## Código fuente

- [IntentExtensions.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/ActivityResultContracts.kt)
- [IntentLauncher.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentLauncher.kt)
- [IntentResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentResult.kt)