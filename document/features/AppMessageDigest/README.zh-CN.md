多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppMessageDigest 使用指南

> 本文档由 ChatGPT 协助完成  
> 源代码链接：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppMessageDigest.kt

## 简介

`AppMessageDigest` 是一个用于计算 MD5 哈希值的工具类，支持对字符串与输入流进行哈希处理，可用于文件完整性校验、唯一标识符生成等场景。支持设置读取块大小及进度回调，适用于大数据处理。

## 功能概览

- 支持对字符串进行 MD5 加密；
- 支持对输入流（如文件）进行分块 MD5 加密；
- 支持计算过程中进度回调；
- 支持自定义读取缓冲区大小。

## 如何使用

### 1. 计算字符串的 MD5 值

```kotlin
val hash = AppMessageDigest.md5("Hello, World!")
// 输出：fc3ff98e8c6a0d3087d515c0473f8677
```

### 2. 计算文件的 MD5 值（带进度回调）

```kotlin
val file = File("/path/to/large_file.zip")
val md5 = AppMessageDigest.md5(file.inputStream(), 4096) { bytesRead ->
    Log.d("Progress", "已读取 $bytesRead 字节")
}
```

### 3. 自定义读取逻辑（内部使用）

该工具类内部采用 `readStream` 方法完成分块读取、处理与进度汇报，开发者通常无需手动调用。

## 推荐使用场景

- 验证下载文件的完整性；
- 对敏感信息进行唯一签名或去重；
- 处理大文件时希望获取读取进度。

## 注意事项

- MD5 并非加密算法，不可用于安全性敏感的场景；
- `InputStream` 使用完后不会自动关闭，请手动处理；
- 当前版本尚未支持中断处理，适合一次性处理完整数据。