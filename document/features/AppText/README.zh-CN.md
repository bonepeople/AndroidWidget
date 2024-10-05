多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppText 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppText.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppText` 是一个字符串处理工具类，提供常见的格式化功能，如字符串补全、数字格式化、文件大小转换、以及字节与十六进制之间的转换。

> 适用于显示优化、文件信息展示、十六进制调试等场景。

## 使用方法

### 1. 补全字符串

#### 前补全：

```kotlin
val result = AppText.completeStart("3", "000")
// 结果："003"
```

#### 后补全：

```kotlin
val result = AppText.completeEnd("3", "###")
// 结果："3##"
```

### 2. 添加千分位分隔符

```kotlin
val formatted = AppText.formatNumber(1234567.89)
// 结果："1,234,567.89"
```

### 3. 自动换行

```kotlin
val wrapped = AppText.wrapString("1234567890123456789", 7)
/*
输出：
1234567
8901234
56789
*/
```

- `length` 参数表示每行最多字符数。
- `separator` 可自定义换行符。

### 4. 格式化文件大小

```kotlin
val size = AppText.formatFileSize(1048576)
// 结果："1 MiB"
```

### 5. 字节数组与十六进制字符串转换

#### 字节数组转十六进制：

```kotlin
val hex = AppText.byteArrayToString(byteArrayOf(0x1F, 0x2B))
// hex: "1f2b"
```

#### 十六进制转字节数组：

```kotlin
val bytes = AppText.stringToByteArray("1f2b")
// bytes: [0x1F, 0x2B]
```