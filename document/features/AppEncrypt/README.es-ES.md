Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppEncrypt

## Introducción

`AppEncrypt` proporciona cifrado y descifrado AES y RSA para cadenas y flujos en proyectos Android.

## Características

- Cifrado y descifrado AES (cadena y flujo)
- Cifrado y descifrado RSA (cadena y flujo)
- Generación y decodificación de pares de claves RSA
- Callbacks opcionales `onProgress` para seguimiento de progreso

## Uso

### AES

Cifrar una cadena:

```kotlin
val encrypted = AppEncrypt.encryptByAES(
    data = "Hello World",
    secret = "your_secret_key",
    salt = "your_salt_key" // opcional
)
```

Descifrar una cadena:

```kotlin
val decrypted = AppEncrypt.decryptByAES(
    data = encrypted,
    secret = "your_secret_key",
    salt = "your_salt_key" // opcional
)
```

Cifrar un flujo de archivo:

```kotlin
AppEncrypt.encryptByAES(
    inputStream = inputFile.inputStream(),
    outputStream = outputFile.outputStream(),
    secret = "your_secret_key"
)
```

### RSA

Generar un par de claves:

```kotlin
val (publicKey, privateKey) = AppEncrypt.generateRSAKeys()
```

Decodificar claves:

```kotlin
val public = AppEncrypt.decodeRSAPublicKey(publicKey)
val private = AppEncrypt.decodeRSAPrivateKey(privateKey)
```

Cifrar/descifrar cadenas:

```kotlin
val encrypted = AppEncrypt.encryptByRSA("Hello", public)
val decrypted = AppEncrypt.decryptByRSA(encrypted, private)
```

## Notas

- Las claves y salts AES se rellenan a 16 caracteres.
- RSA soporta el formato PKCS#8.
- Transformaciones predeterminadas: `AES/CBC/PKCS5Padding` y `RSA/ECB/PKCS1Padding`.

## Código fuente

[AppEncrypt.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppEncrypt.kt)