Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppMessageDigest

## Introducción

`AppMessageDigest` calcula hashes MD5 de cadenas y flujos de entrada. Soporta seguimiento de progreso durante el procesamiento de flujos, ideal para verificación de integridad de archivos y huellas de contenido.

## Casos de uso

- Validar archivos descargados mediante hashes MD5
- Crear huellas de contenido o deduplicar cadenas
- Mostrar progreso al hashear archivos grandes

## Características

- Hash MD5 de contenido de cadena
- Hash MD5 de `InputStream` con lectura por fragmentos
- Callback de progreso para bytes procesados
- Tamaño de búfer de lectura personalizable

## Uso

Hashear una cadena:

```kotlin
val hash = AppMessageDigest.md5("Hello, World!")
// Salida: fc3ff98e8c6a0d3087d515c0473f8677
```

Hashear un archivo con retroalimentación de progreso:

```kotlin
val file = File("/path/to/large_file.zip")
val md5 = AppMessageDigest.md5(file.inputStream(), 4096) { bytesRead ->
    Log.d("Progress", "Read $bytesRead bytes")
}
```

## Notas

- MD5 no es un método de cifrado seguro — no lo uses para datos sensibles.
- Los flujos no se cierran automáticamente — ciérralos manualmente.
- El soporte de interrupción no está implementado en esta versión.

## Código fuente

[AppMessageDigest.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppMessageDigest.kt)