Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppPermission

## Introducción

`AppPermission` simplifica la comprobación y solicitud de permisos en tiempo de ejecución en Android. Usa `ActivityResultContract` internamente y dispara el flujo de solicitud inmediatamente al llamar `request()`.

## Características

- Comprobar o solicitar múltiples permisos en una sola llamada
- Solo solicita permisos aún no concedidos
- Callback `onGranted` cuando todos los permisos solicitados están concedidos
- Callback `onResult` con mapa detallado del estado de permisos

A diferencia de otros frameworks, llamar `AppPermission.request()` dispara inmediatamente el proceso de solicitud. No necesitas configurar callbacks por separado ni invocar una operación de «aplicar».

## Uso

Comprobar permisos:

```kotlin
AppPermission.check(android.Manifest.permission.CAMERA)
    .onGranted {
        // Todos los permisos solicitados están concedidos
    }
    .onResult { allGranted, resultMap ->
        // Gestionar el mapa de resultados si es necesario
    }
```

Solicitar permisos:

```kotlin
AppPermission.request(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.READ_EXTERNAL_STORAGE
).onGranted {
    // Todos los permisos concedidos
}.onResult { allGranted, resultMap ->
    if (!allGranted) {
        // Gestionar permisos denegados
    }
}
```

## Notas

- `onGranted` solo se dispara cuando **todos** los permisos solicitados están concedidos. Usa `onResult` cuando necesites gestionar casos de denegación.

## Código fuente

[AppPermission.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt)