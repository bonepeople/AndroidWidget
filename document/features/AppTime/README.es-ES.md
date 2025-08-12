Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppTime

## Introducción

`AppTime` formatea y convierte marcas de tiempo y duraciones. Soporta patrones de salida personalizados, zonas horarias y formateo específico por locale.

## Casos de uso

- Cadenas de tiempo legibles en logs, UI e informes
- Visualización de duración estilo temporizador

## Características

- Formateo de marcas de tiempo con milisegundos opcionales
- Soporte de patrón, zona horaria y locale personalizados
- Formateo de duración como cadena de temporizador (unidades completas y control de milisegundos opcionales)
- Compatible con versiones anteriores y posteriores a API 26

## Uso

Formatear marca de tiempo como fecha-hora legible:

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis())
// "2025/6/30 14:33:45"

val withMillis = AppTime.getDateTimeString(System.currentTimeMillis(), withMillis = true)
// "2025/6/30 14:33:45.123"
```

Formatear con un patrón personalizado:

```kotlin
val result = AppTime.formatTime(
    timestamp = System.currentTimeMillis(),
    pattern = "yyyy-MM-dd HH:mm:ss.SSS",
    timeZone = TimeZone.getTimeZone("GMT+8"),
    local = Locale.US
)
// "2025-06-30 14:33:45.123"
```

Formatear duración (milisegundos) como cadena de temporizador:

```kotlin
val timer = AppTime.getTimeString(3661999)
// "1:01:01.999"

val fullTimer = AppTime.getTimeString(5999, fullTimeString = true)
// "00:00:05.999"

val noMillis = AppTime.getTimeString(5999, withMillis = false)
// "5"
```

## Código fuente

[AppTime.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppTime.kt)