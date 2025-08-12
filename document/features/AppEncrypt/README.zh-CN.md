多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppEncrypt

## 简介

`AppEncrypt` 提供 Android 项目中字符串与流的 AES、RSA 加解密能力。

## 功能

- AES 加解密（字符串与流）
- RSA 加解密（字符串与流）
- RSA 密钥对生成与解码
- 可选的 `onProgress` 进度回调

## 使用方式

### AES

加密字符串：

```kotlin
val encrypted = AppEncrypt.encryptByAES(
    data = "Hello World",
    secret = "your_secret_key",
    salt = "your_salt_key" // 可选
)
```

解密字符串：

```kotlin
val decrypted = AppEncrypt.decryptByAES(
    data = encrypted,
    secret = "your_secret_key",
    salt = "your_salt_key" // 可选
)
```

加密文件流：

```kotlin
AppEncrypt.encryptByAES(
    inputStream = inputFile.inputStream(),
    outputStream = outputFile.outputStream(),
    secret = "your_secret_key"
)
```

### RSA

生成密钥对：

```kotlin
val (publicKey, privateKey) = AppEncrypt.generateRSAKeys()
```

解码密钥：

```kotlin
val public = AppEncrypt.decodeRSAPublicKey(publicKey)
val private = AppEncrypt.decodeRSAPrivateKey(privateKey)
```

加解密字符串：

```kotlin
val encrypted = AppEncrypt.encryptByRSA("Hello", public)
val decrypted = AppEncrypt.decryptByRSA(encrypted, private)
```

## 注意事项

- AES 密钥和 salt 会填充至 16 个字符。
- RSA 支持 PKCS#8 格式。
- 默认算法：`AES/CBC/PKCS5Padding` 和 `RSA/ECB/PKCS1Padding`。

## 源码链接

[AppEncrypt.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppEncrypt.kt)