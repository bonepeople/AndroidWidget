Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppText Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppText.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppText` is a string utility class providing common formatting operations such as number formatting, string padding, file size conversion, and byte/hex transformations.

> Especially useful for display formatting, file size readability, data conversion, and UI-friendly output.

## How to Use

### 1. Padding Strings

#### Add characters to the beginning:

```kotlin
val result = AppText.completeStart("3", "000")
// result: "003"
```

#### Add characters to the end:

```kotlin
val result = AppText.completeEnd("3", "###")
// result: "3##"
```

### 2. Format Numbers with Thousands Separator

```kotlin
val formatted = AppText.formatNumber(1234567.89)
// formatted: "1,234,567.89"
```

### 3. Wrap Long Strings into Lines

```kotlin
val wrapped = AppText.wrapString("1234567890123456789", 7)
/*
Output:
1234567
8901234
56789
*/
```

- The `length` parameter controls the maximum characters per line.
- You can customize the line separator if needed.

### 4. Format File Sizes

```kotlin
val readable = AppText.formatFileSize(1048576)
// readable: "1 MiB"
```

### 5. Byte Array and Hex String Conversion

#### Convert byte array to hex string:

```kotlin
val hex = AppText.byteArrayToString(byteArrayOf(0x1F, 0x2B))
// hex: "1f2b"
```

#### Convert hex string to byte array:

```kotlin
val bytes = AppText.stringToByteArray("1f2b")
// bytes: [0x1F, 0x2B]
```