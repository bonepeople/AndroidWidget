Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppTime

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppTime.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppTime` es una clase utilitaria para convertir y formatear fechas, horas y duraciones. Permite configurar zona horaria, patrón de salida y formato local.

> Útil para mostrar fechas y duraciones legibles en la interfaz o en registros.

## Cómo usarlo

### 1. Formatear marca de tiempo como fecha legible

```kotlin
val resultado = AppTime.getDateTimeString(System.currentTimeMillis())
// resultado: "2025/6/30 14:33:45"
```

#### Incluir milisegundos:

```kotlin
val resultado = AppTime.getDateTimeString(System.currentTimeMillis(), withMillis = true)
// resultado: "2025/6/30 14:33:45.123"
```

### 2. Formatear usando un patrón personalizado

```kotlin
val resultado = AppTime.formatTime(
    timestamp = System.currentTimeMillis(),
    pattern = "yyyy-MM-dd HH:mm:ss.SSS",
    timeZone = TimeZone.getTimeZone("GMT+2"),
    local = Locale("es", "ES")
)
// resultado: "2025-06-30 08:33:45.123"
```

### 3. Convertir duración (milisegundos) a formato de temporizador

```kotlin
val temporizador = AppTime.getTimeString(3661999)
// resultado: "1:01:01.999"
```

#### Mostrar siempre horas y minutos:

```kotlin
val temporizador = AppTime.getTimeString(5999, fullTimeString = true)
// resultado: "00:00:05.999"
```

#### Ocultar milisegundos:

```kotlin
val temporizador = AppTime.getTimeString(5999, withMillis = false)
// resultado: "5"
```