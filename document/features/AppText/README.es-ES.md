Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppText

## Introducción

`AppText` es una utilidad de cadenas que proporciona operaciones de formateo comunes como formateo numérico, relleno de cadenas, conversión de tamaño de archivos y transformaciones byte/hexadecimal.

## Casos de uso

- Formateo de visualización y salida amigable para UI
- Etiquetas legibles de tamaño de archivo
- Conversión entre arrays de bytes y cadenas hexadecimales

## Características

- Relleno de cadenas (inicio y fin)
- Formateo numérico con separador de miles
- Ajuste de líneas en cadenas largas
- Formateo de tamaño de archivo (p. ej. `"1 MiB"`)
- Conversión entre array de bytes y cadena hexadecimal

## Uso

Relleno de cadenas:

```kotlin
val start = AppText.completeStart("3", "000") // "003"
val end = AppText.completeEnd("3", "###")     // "3##"
```

Formatear números con separador de miles:

```kotlin
val formatted = AppText.formatNumber(1234567.89)
// "1,234,567.89"
```

Ajustar cadenas largas en líneas:

```kotlin
val wrapped = AppText.wrapString("1234567890123456789", 7)
/*
1234567
8901234
56789
*/
```

El parámetro `length` controla el máximo de caracteres por línea. Puedes personalizar el separador de línea si es necesario.

Formatear tamaños de archivo:

```kotlin
val readable = AppText.formatFileSize(1048576)
// "1 MiB"
```

Conversión entre array de bytes y cadena hexadecimal:

```kotlin
val hex = AppText.byteArrayToString(byteArrayOf(0x1F, 0x2B)) // "1f2b"
val bytes = AppText.stringToByteArray("1f2b")                // [0x1F, 0x2B]
```

## Código fuente

[AppText.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppText.kt)