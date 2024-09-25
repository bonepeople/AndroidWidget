Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Utilidad AppEncrypt

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppEncrypt.kt

Este documento describe cómo utilizar la clase de utilidad `AppEncrypt` para realizar operaciones de cifrado y descifrado AES y RSA en proyectos Android.

> *Este documento fue generado con la ayuda de ChatGPT.*

---

## Funcionalidades

* Cifrado y descifrado con AES (por flujo y cadena)
* Cifrado y descifrado con RSA (por flujo y cadena)
* Generación y decodificación de claves RSA

---

## Uso de AES

### Cifrar una cadena

```kotlin
val encrypted = AppEncrypt.encryptByAES(
    data = "Hola Mundo",
    secret = "tu_clave_secreta",
    salt = "tu_salt" // opcional
)
```

### Descifrar una cadena

```kotlin
val decrypted = AppEncrypt.decryptByAES(
    data = encrypted,
    secret = "tu_clave_secreta",
    salt = "tu_salt" // opcional
)
```

### Cifrar un flujo de archivo

```kotlin
AppEncrypt.encryptByAES(
    inputStream = inputFile.inputStream(),
    outputStream = outputFile.outputStream(),
    secret = "tu_clave_secreta"
)
```

---

## Uso de RSA

### Generar un par de claves

```kotlin
val (publicKey, privateKey) = AppEncrypt.generateRSAKeys()
```

### Decodificar claves

```kotlin
val public = AppEncrypt.decodeRSAPublicKey(publicKey)
val private = AppEncrypt.decodeRSAPrivateKey(privateKey)
```

### Cifrar/Descifrar cadenas

```kotlin
val encrypted = AppEncrypt.encryptByRSA("Hola", public)
val decrypted = AppEncrypt.decryptByRSA(encrypted, private)
```

---

## Notas

* Las claves y saltos AES se rellenan a 16 caracteres.
* RSA es compatible con el formato PKCS#8.
* Transformaciones predeterminadas: `AES/CBC/PKCS5Padding` y `RSA/ECB/PKCS1Padding`
* Soporte opcional para `onProgress` para monitoreo de rendimiento.