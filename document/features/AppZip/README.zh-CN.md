多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppZip

## 简介

`AppZip` 提供文件压缩与解压工具，支持单文件压缩、多文件/目录压缩及 zip 解压，并保持目录结构。

## 功能

- 将单个文件压缩为 zip 条目
- 压缩多个文件或目录并保留结构
- 解压 zip 到目标目录
- zip 条目名使用 UTF-8 编码

## 使用方式

压缩单个文件：

```kotlin
val inputFile = File("example.txt")
val outputFile = File("example.zip")

AppZip.zipFile("example.txt", inputFile.inputStream(), outputFile.outputStream())
```

压缩多个文件或目录：

```kotlin
AppZip.zipFiles(
    File("output.zip").outputStream(),
    File("file1.txt"),
    File("dir2")
)
```

解压 zip 文件：

```kotlin
AppZip.unzipFile(
    File("example.zip").inputStream(),
    File("output_directory")
)
```

## 注意事项

- 压缩时会保留空目录。
- 文件名使用 UTF-8 编码存储（现代 Java 中 `ZipEntry` 的默认值）。
- 若 zip 条目非 UTF-8 编码会抛出异常。

## 源码链接

[AppZip.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppZip.kt)