Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppData

## Introducción

`AppData` es una utilidad de almacenamiento clave-valor basada en Jetpack DataStore, diseñada para persistir datos de configuración de forma eficiente en aplicaciones Android. Soporta acceso asíncrono (suspend) y bloqueante (síncrono), así como múltiples instancias aisladas por nombre.

## Casos de uso

- Reemplazar `SharedPreferences` con una solución moderna y segura para corrutinas
- Gestionar configuraciones para múltiples usuarios o módulos
- Monitorizar cambios de preferencias mediante flujos reactivos
- Rastrear versiones de datos en múltiples almacenes de preferencias

## Características

- Lectura y escritura de tipos básicos (String, Int, Long, Float, Double, Boolean)
- Funciones suspend y API síncrona
- Acceso reactivo con `Flow`
- Múltiples instancias de almacenamiento con nombre
- Gestión automática de versión y registro

## Uso

Usar el almacén de datos predeterminado:

```kotlin
val data = AppData.default
```

Crear un almacén con nombre personalizado:

```kotlin
val config = AppData.create("settings")
```

Guardar datos (suspend):

```kotlin
runBlocking {
    data.putString("username", "jack")
    data.putInt("launchCount", 5)
}
```

Recuperar datos (suspend):

```kotlin
val username = data.getString("username", "guest")
```

Guardar/recuperar datos (síncrono):

```kotlin
data.putBooleanSync("night_mode", true)
val enabled = data.getBooleanSync("night_mode", false)
```

Observar cambios con `Flow`:

```kotlin
data.getIntFlow("launchCount").collect {
    Log.d("Launch Count", "$it")
}
```

## Notas

- `storeName` no debe contener caracteres ilegales (`<>:"/\|?*`) ni terminar en punto o espacio.
- Las API síncronas usan `runBlocking` — evita llamarlas en el hilo principal.

## Código fuente

[AppData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppData.kt)