Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppEncrypt

## Introduction

`AppEncrypt` provides AES and RSA encryption and decryption for strings and streams in Android projects.

## Features

- AES encryption and decryption (string and stream)
- RSA encryption and decryption (string and stream)
- RSA key pair generation and decoding
- Optional `onProgress` callbacks for performance monitoring

## Usage

### AES

Encrypt a string:

```kotlin
val encrypted = AppEncrypt.encryptByAES(
    data = "Hello World",
    secret = "your_secret_key",
    salt = "your_salt_key" // optional
)
```

Decrypt a string:

```kotlin
val decrypted = AppEncrypt.decryptByAES(
    data = encrypted,
    secret = "your_secret_key",
    salt = "your_salt_key" // optional
)
```

Encrypt a file stream:

```kotlin
AppEncrypt.encryptByAES(
    inputStream = inputFile.inputStream(),
    outputStream = outputFile.outputStream(),
    secret = "your_secret_key"
)
```

### RSA

Generate a key pair:

```kotlin
val (publicKey, privateKey) = AppEncrypt.generateRSAKeys()
```

Decode keys:

```kotlin
val public = AppEncrypt.decodeRSAPublicKey(publicKey)
val private = AppEncrypt.decodeRSAPrivateKey(privateKey)
```

Encrypt/decrypt strings:

```kotlin
val encrypted = AppEncrypt.encryptByRSA("Hello", public)
val decrypted = AppEncrypt.decryptByRSA(encrypted, private)
```

## Notes

- AES keys and salts are padded to 16 characters.
- RSA supports the PKCS#8 format.
- Default transformations: `AES/CBC/PKCS5Padding` and `RSA/ECB/PKCS1Padding`.

## Source Code

[AppEncrypt.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppEncrypt.kt)