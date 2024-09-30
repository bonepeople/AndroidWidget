Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppMessageDigest

> Este documento fue elaborado con la ayuda de ChatGPT  
> Enlace al código fuente: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppMessageDigest.kt

## Introducción

`AppMessageDigest` es una clase utilitaria para calcular valores hash MD5 a partir de cadenas de texto o flujos de entrada (`InputStream`). Es útil para verificar la integridad de archivos, comparar contenido o generar identificadores únicos. También admite devolución de progreso durante el procesamiento de datos.

## Características

- Calcula MD5 a partir de una cadena (`String`);
- Calcula MD5 de archivos u otros `InputStream` en bloques;
- Permite seguimiento de progreso en tiempo real;
- Se puede definir el tamaño del búfer de lectura.

## Cómo usar

### 1. Calcular MD5 de una cadena

```kotlin
val hash = AppMessageDigest.md5("Hola Mundo")
// Resultado: fc3ff98e8c6a0d3087d515c0473f8677
```

### 2. Calcular MD5 de un archivo con progreso

```kotlin
val file = File("/ruta/a/archivo_grande.zip")
val md5 = AppMessageDigest.md5(file.inputStream(), 4096) { bytesRead ->
    Log.d("Progreso", "Leídos $bytesRead bytes")
}
```

### 3. Lógica personalizada de lectura (uso interno)

El método `readStream` se usa internamente para leer en bloques y notificar progreso. No es necesario llamarlo directamente.

## Casos recomendados de uso

- Verificar la integridad de archivos descargados;
- Generar firmas o claves únicas para contenidos;
- Monitorear progreso al procesar archivos grandes.

## Notas

- MD5 no es seguro para datos confidenciales;
- El `InputStream` no se cierra automáticamente: debes cerrarlo manualmente;
- Actualmente no se admite interrupción del cálculo.