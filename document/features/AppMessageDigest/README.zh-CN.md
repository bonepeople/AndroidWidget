多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppMessageDigest

## 简介

`AppMessageDigest` 用于计算字符串和输入流的 MD5 哈希值，支持流处理过程中的进度回调，适用于文件完整性校验和内容指纹生成。

## 场景

- 通过 MD5 校验下载文件
- 生成内容指纹或字符串去重
- 哈希大文件时展示进度

## 功能

- 字符串 MD5 哈希
- `InputStream` 分块读取 MD5 哈希
- 已读字节数进度回调
- 可自定义流读取缓冲区大小

## 使用方式

哈希字符串：

```kotlin
val hash = AppMessageDigest.md5("Hello, World!")
// 输出: fc3ff98e8c6a0d3087d515c0473f8677
```

哈希文件并反馈进度：

```kotlin
val file = File("/path/to/large_file.zip")
val md5 = AppMessageDigest.md5(file.inputStream(), 4096) { bytesRead ->
    Log.d("Progress", "Read $bytesRead bytes")
}
```

## 注意事项

- MD5 不是安全的加密算法，请勿用于敏感数据。
- 流不会自动关闭，需手动关闭。
- 当前版本不支持中断操作。

## 源码链接

[AppMessageDigest.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppMessageDigest.kt)