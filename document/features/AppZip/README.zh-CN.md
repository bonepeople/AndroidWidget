多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppZip 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppZip.kt  
**本文档由 ChatGPT 协助生成。**

## 简介

`AppZip` 是一个用于文件压缩与解压的工具类。  
支持单个文件压缩、多文件或目录压缩，以及 `.zip` 文件解压缩。

## 功能说明

### 1. 压缩单个文件

```kotlin
val 输入文件 = File("示例.txt")
val 压缩文件 = File("示例.zip")

AppZip.zipFile("示例.txt", 输入文件.inputStream(), 压缩文件.outputStream())
```

### 2. 压缩多个文件或目录

```kotlin
AppZip.zipFiles(
    File("压缩输出.zip").outputStream(),
    File("文件1.txt"),
    File("目录2")
)
```

目录结构会被保留在压缩包中。

### 3. 解压 Zip 文件

```kotlin
AppZip.unzipFile(
    File("示例.zip").inputStream(),
    File("解压目录")
)
```

**注意**：当 ZIP 中的文件名不是 UTF-8 编码时会抛出异常。

## 补充说明

- 支持空目录压缩。
- 默认使用 UTF-8 编码存储文件名，兼容性好。