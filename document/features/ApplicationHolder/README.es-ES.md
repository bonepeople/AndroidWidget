Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# ApplicationHolder

## Introducción

`ApplicationHolder` proporciona acceso centralizado a la instancia de `Application` y a información clave a nivel de aplicación, como el estado de depuración, la versión y el nombre del paquete.

Se inicializa automáticamente mediante Jetpack Startup, sin configuración manual.

## Características

- Acceso al contexto global de `Application`
- Comprobar si la app se ejecuta en modo depuración
- Leer nombre de versión, código de versión y nombre del paquete

## Uso

Tras el inicio de la app, úsalo desde cualquier parte:

```kotlin
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val versionName = ApplicationHolder.getVersionName()
val versionCode = ApplicationHolder.getVersionCode()
val packageName = ApplicationHolder.getPackageName()
```

Valores típicos:

- `debug`: `true` si la app se compila en modo depuración
- `getVersionName()`: p. ej. `"1.0.0"`
- `getVersionCode()`: p. ej. `10000`

## Notas

- Acceder a `ApplicationHolder` antes de la inicialización lanza una excepción.

## Código fuente

[ApplicationHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ApplicationHolder.kt)