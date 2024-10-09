Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de Uso de AppZip

**Código Fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppZip.kt  
**Este documento fue generado con la ayuda de ChatGPT.**

## Descripción General

`AppZip` es una utilidad para comprimir y descomprimir archivos y carpetas.  
Permite comprimir un solo archivo, múltiples archivos o directorios, así como descomprimir archivos `.zip`.

## Funciones

### 1. Comprimir un solo archivo

```kotlin
val archivoEntrada = File("ejemplo.txt")
val archivoZip = File("ejemplo.zip")

AppZip.zipFile("ejemplo.txt", archivoEntrada.inputStream(), archivoZip.outputStream())
```

### 2. Comprimir múltiples archivos o carpetas

```kotlin
AppZip.zipFiles(
    File("salida.zip").outputStream(),
    File("archivo1.txt"),
    File("carpeta2")
)
```

La estructura de carpetas se mantiene en el archivo `.zip`.

### 3. Descomprimir archivo zip

```kotlin
AppZip.unzipFile(
    File("ejemplo.zip").inputStream(),
    File("directorio_destino")
)
```

**Nota**: Lanza una excepción si el archivo zip no está codificado en UTF-8.

## Observaciones

- Los directorios vacíos se mantienen en la compresión.
- Los nombres de archivos se almacenan con codificación UTF-8.