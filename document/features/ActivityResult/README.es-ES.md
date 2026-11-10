Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# IntentLauncher

## Introducción

`IntentLauncher` simplifica el flujo de `startActivityForResult` en Android. Lanza una `Activity` con la extensión `.launch()` y gestiona el resultado mediante callbacks encadenados o suspensión de corrutinas.

El monitoreo del ciclo de vida y el registro del launcher se gestionan automáticamente, sin configuración manual.

## Características

- Manejadores encadenados `.onSuccess`, `.onFailure` y `.onResult`
- Compatibilidad con `.await()` para suspender corrutinas
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

Si la lógica posterior de una corrutina necesita esperar a que vuelva la Activity, se puede llamar a `await()` después de `launch()` para suspender la ejecución hasta que el resultado esté disponible:

```kotlin
Intent(this, DetailActivity::class.java)
    .launch()
    .await()
    .onSuccess { data ->
        // Continúa con el resultado correcto después de que vuelva la Activity
    }
    .onFailure { result ->
        // Gestiona un resultado distinto de RESULT_OK después de que vuelva la Activity
    }
```

`await()` es opcional; las llamadas existentes pueden seguir encadenando los manejadores directamente después de `launch()`. Cuando se usa, devuelve el mismo `IntentResult` una vez disponible el resultado, por lo que los manejadores posteriores solo se ejecutan cuando vuelve la Activity. Cancelar la corrutina en espera no cancela la Activity iniciada. El mismo `IntentResult` aún puede gestionarse mediante callbacks o esperarse desde otra corrutina.

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