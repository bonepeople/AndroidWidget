Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppEncrypt Utility

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppEncrypt.kt

This document describes how to use the `AppEncrypt` utility class for performing AES and RSA encryption and decryption in Android projects.

> *This document was generated with assistance from ChatGPT.*

---

## Features

* AES encryption and decryption (stream and string-based)
* RSA encryption and decryption (stream and string-based)
* RSA key generation and decoding

---

## AES Usage

### Encrypt a String

```kotlin
val encrypted = AppEncrypt.encryptByAES(
    data = "Hello World",
    secret = "your_secret_key",
    salt = "your_salt_key" // optional
)
```

### Decrypt a String

```kotlin
val decrypted = AppEncrypt.decryptByAES(
    data = encrypted,
    secret = "your_secret_key",
    salt = "your_salt_key" // optional
)
```

### Encrypt a File Stream

```kotlin
AppEncrypt.encryptByAES(
    inputStream = inputFile.inputStream(),
    outputStream = outputFile.outputStream(),
    secret = "your_secret_key"
)
```

---

## RSA Usage

### Generate Key Pair

```kotlin
val (publicKey, privateKey) = AppEncrypt.generateRSAKeys()
```

### Decode Keys

```kotlin
val public = AppEncrypt.decodeRSAPublicKey(publicKey)
val private = AppEncrypt.decodeRSAPrivateKey(privateKey)
```

### Encrypt/Decrypt Strings

```kotlin
val encrypted = AppEncrypt.encryptByRSA("Hello", public)
val decrypted = AppEncrypt.decryptByRSA(encrypted, private)
```

---

## Notes

* AES keys and salts are padded to 16 characters.
* RSA supports the PKCS#8 format.
* Uses default transformations: `AES/CBC/PKCS5Padding` and `RSA/ECB/PKCS1Padding`
* For performance monitoring, optional `onProgress` callbacks are supported.