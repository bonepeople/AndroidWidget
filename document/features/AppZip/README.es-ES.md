Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppZip

## Introducción

`AppZip` proporciona utilidades de compresión y descompresión de archivos. Soporta compresión de un solo archivo, compresión de múltiples archivos/directorios y extracción de archivos zip, conservando la estructura de directorios.

## Características

- Comprimir un solo archivo en una entrada zip
- Comprimir múltiples archivos o directorios conservando la estructura
- Extraer archivos zip a un directorio destino
- Codificación UTF-8 para nombres de entradas zip

## Uso

Comprimir un solo archivo:

```kotlin
val inputFile = File("example.txt")
val outputFile = File("example.zip")

AppZip.zipFile("example.txt", inputFile.inputStream(), outputFile.outputStream())
```

Comprimir múltiples archivos o directorios:

```kotlin
AppZip.zipFiles(
    File("output.zip").outputStream(),
    File("file1.txt"),
    File("dir2")
)
```

Extraer un archivo zip:

```kotlin
AppZip.unzipFile(
    File("example.zip").inputStream(),
    File("output_directory")
)
```

## Notas

- Los directorios vacíos se conservan durante la compresión.
- Los nombres de archivo se almacenan con codificación UTF-8 (valor predeterminado de `ZipEntry` en Java moderno).
- Lanza una excepción si las entradas zip no están codificadas en UTF-8.

## Código fuente

[AppZip.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppZip.kt)