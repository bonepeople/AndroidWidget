Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# ApplicationHolder

**Enlace al código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ApplicationHolder.kt

`ApplicationHolder` es una utilidad centralizada que permite acceder a información clave de la aplicación y a la instancia de `Application` desde cualquier parte de tu app Android.

Se inicializa automáticamente mediante Jetpack Startup para facilitar el acceso global.

> 📄 Esta documentación fue asistida por ChatGPT.

## Uso

Este objeto se inicializa automáticamente utilizando el mecanismo de inicialización de AndroidX. Una vez inicializado, puedes usarlo en cualquier parte de tu aplicación.

### Ejemplo

```kotlin
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val versionName = ApplicationHolder.getVersionName()
val versionCode = ApplicationHolder.getVersionCode()
val packageName = ApplicationHolder.getPackageName()
```

### Resultados Típicos

- `debug`: `true` si la app fue compilada en modo debug.
- `getVersionName()`: devuelve el nombre de versión de la app (ejemplo: `"1.0.0"`).
- `getVersionCode()`: devuelve el código de versión de la app (ejemplo: `10000`).

## Notas

- Si accedes a `ApplicationHolder` antes de su inicialización, se lanzará una excepción.