多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppEncrypt 加密工具

**源码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppEncrypt.kt

本文档介绍如何在 Android 项目中使用 `AppEncrypt` 工具类实现 AES 与 RSA 的加解密功能。

> *本文档由 ChatGPT 协助完成。*

---

## 功能简介

* 支持 AES 加解密（流式与字符串方式）
* 支持 RSA 加解密（流式与字符串方式）
* 支持 RSA 密钥对生成与解码

---

## AES 用法

### 加密字符串

```kotlin
val encrypted = AppEncrypt.encryptByAES(
    data = "你好，世界",
    secret = "你的密钥",
    salt = "可选的盐值"
)
```

### 解密字符串

```kotlin
val decrypted = AppEncrypt.decryptByAES(
    data = encrypted,
    secret = "你的密钥",
    salt = "可选的盐值"
)
```

### 加密文件流

```kotlin
AppEncrypt.encryptByAES(
    inputStream = inputFile.inputStream(),
    outputStream = outputFile.outputStream(),
    secret = "你的密钥"
)
```

---

## RSA 用法

### 生成密钥对

```kotlin
val (publicKey, privateKey) = AppEncrypt.generateRSAKeys()
```

### 解码密钥

```kotlin
val public = AppEncrypt.decodeRSAPublicKey(publicKey)
val private = AppEncrypt.decodeRSAPrivateKey(privateKey)
```

### 加密/解密字符串

```kotlin
val encrypted = AppEncrypt.encryptByRSA("你好", public)
val decrypted = AppEncrypt.decryptByRSA(encrypted, private)
```

---

## 注意事项

* AES 的密钥与盐值不足 16 位会自动补齐空格。
* RSA 支持 PKCS#8 格式密钥。
* 默认加密方式为 `AES/CBC/PKCS5Padding` 和 `RSA/ECB/PKCS1Padding`
* 所有加解密方法均支持可选的进度回调 `onProgress`