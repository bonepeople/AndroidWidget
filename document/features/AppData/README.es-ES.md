Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppData

> Este documento fue elaborado con la ayuda de ChatGPT  
> Enlace al código fuente: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppData.kt

## Introducción

`AppData` es una clase utilitaria basada en Jetpack DataStore para almacenar datos persistentes como pares clave-valor en aplicaciones Android. Soporta tanto acceso asincrónico (`suspend`) como sincrónico (bloqueante), y permite múltiples instancias aisladas mediante nombres personalizados.

## Características

- Lectura y escritura de tipos básicos (String, Int, Long, Float, Double, Boolean);
- Métodos suspendidos y métodos sincrónicos;
- Acceso reactivo con `Flow`;
- Soporte para múltiples almacenes de datos nombrados;
- Mantenimiento automático de la versión y tabla de registro.

## Cómo usar

### 1. Obtener el almacén de datos por defecto

```kotlin
val data = AppData.default
```

### 2. Crear un almacén de datos con nombre

```kotlin
val config = AppData.create("settings")
```

### 3. Guardar datos (suspend)

```kotlin
runBlocking {
    data.putString("username", "jack")
    data.putInt("launchCount", 5)
}
```

### 4. Leer datos (suspend)

```kotlin
val username = data.getString("username", "guest")
```

### 5. Guardar/leer datos (sincrónico)

```kotlin
data.putBooleanSync("night_mode", true)
val enabled = data.getBooleanSync("night_mode", false)
```

### 6. Escuchar cambios con Flow

```kotlin
data.getIntFlow("launchCount").collect {
    Log.d("Launch Count", "$it")
}
```

## Casos recomendados de uso

- Reemplazar `SharedPreferences` con una solución moderna y segura para corutinas;
- Manejar configuraciones para múltiples usuarios o módulos;
- Monitorear cambios de preferencias en tiempo real con `Flow`;
- Llevar control de versiones y registros de configuración.

## Notas

- El nombre `storeName` no debe contener caracteres ilegales (`<>:"/\\|?*`) ni terminar en punto o espacio;
- Las funciones sincrónicas utilizan `runBlocking`, se recomienda no llamarlas en el hilo principal;