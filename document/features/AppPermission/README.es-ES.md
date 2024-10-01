Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppPermission

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppPermission` es una clase utilitaria para gestionar permisos en tiempo de ejecución en aplicaciones Android. Usa `ActivityResultContract` para simplificar el proceso de solicitud de permisos.

> Esta utilidad está diseñada para ser simple y flexible.
> - Puedes solicitar varios permisos a la vez.
> - Solo se solicitarán los permisos que aún no hayan sido concedidos.
> - A diferencia de otros frameworks, `AppPermission.request()` inicia el proceso de solicitud de inmediato. No estás obligado a definir callbacks ni a llamar a una operación de "apply".
> - El callback `onGranted` solo se activa si **todos** los permisos solicitados han sido concedidos. Es ideal para casos en los que no necesitas manejar la denegación de permisos.

## Cómo usarlo

### 1. Verificar permisos

Puedes verificar si los permisos ya han sido concedidos usando el método `check()`:

```kotlin
val permiso = AppPermission.check(android.Manifest.permission.CAMERA)
    .onGranted {
        // Todos los permisos solicitados han sido concedidos
    }
    .onResult { todosConcedidos, mapaResultado ->
        // Maneja el resultado si es necesario
    }
```

### 2. Solicitar permisos

Para solicitar permisos al usuario:

```kotlin
val permiso = AppPermission.request(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.READ_EXTERNAL_STORAGE
).onGranted {
    // Todos los permisos han sido concedidos
}.onResult { todosConcedidos, mapaResultado ->
    if (!todosConcedidos) {
        // Manejar permisos denegados
    }
}
```