Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppText

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppText.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppText` es una clase utilitaria para manipulación y formato de cadenas, incluyendo relleno, formato de números, conversión de tamaños de archivo y transformación de bytes/hexadecimal.

> Útil para mostrar datos formateados, manipular cadenas y convertir entre representaciones.

## Cómo usarlo

### 1. Rellenar cadenas

#### Rellenar al principio:

```kotlin
val resultado = AppText.completeStart("3", "000")
// resultado: "003"
```

#### Rellenar al final:

```kotlin
val resultado = AppText.completeEnd("3", "###")
// resultado: "3##"
```

### 2. Formatear números con separador de miles

```kotlin
val formateado = AppText.formatNumber(1234567.89)
// formateado: "1,234,567.89"
```

### 3. Dividir texto largo en líneas

```kotlin
val envuelto = AppText.wrapString("1234567890123456789", 7)
/*
Salida:
1234567
8901234
56789
*/
```

### 4. Formatear tamaño de archivo

```kotlin
val tamaño = AppText.formatFileSize(1048576)
// tamaño: "1 MiB"
```

### 5. Conversión entre bytes y cadena hexadecimal

#### ByteArray a hex:

```kotlin
val hex = AppText.byteArrayToString(byteArrayOf(0x1F, 0x2B))
// hex: "1f2b"
```

#### Hex a ByteArray:

```kotlin
val bytes = AppText.stringToByteArray("1f2b")
// bytes: [0x1F, 0x2B]
```