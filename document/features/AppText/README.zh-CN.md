多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppText

## 简介

`AppText` 是字符串工具类，提供数字格式化、字符串填充、文件大小转换及字节/十六进制转换等常用操作。

## 场景

- 展示格式化与 UI 友好输出
- 可读的文件大小标签
- 字节数组与十六进制字符串互转

## 功能

- 字符串填充（首部与尾部）
- 千分位数字格式化
- 长字符串换行
- 文件大小格式化（如 `"1 MiB"`）
- 字节数组与十六进制字符串互转

## 使用方式

字符串填充：

```kotlin
val start = AppText.completeStart("3", "000") // "003"
val end = AppText.completeEnd("3", "###")     // "3##"
```

千分位数字格式化：

```kotlin
val formatted = AppText.formatNumber(1234567.89)
// "1,234,567.89"
```

长字符串换行：

```kotlin
val wrapped = AppText.wrapString("1234567890123456789", 7)
/*
1234567
8901234
56789
*/
```

`length` 参数控制每行最大字符数，可按需自定义行分隔符。

文件大小格式化：

```kotlin
val readable = AppText.formatFileSize(1048576)
// "1 MiB"
```

字节数组与十六进制互转：

```kotlin
val hex = AppText.byteArrayToString(byteArrayOf(0x1F, 0x2B)) // "1f2b"
val bytes = AppText.stringToByteArray("1f2b")                // [0x1F, 0x2B]
```

## 源码链接

[AppText.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppText.kt)