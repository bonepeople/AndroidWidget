Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppText

## Introduction

`AppText` is a string utility providing common formatting operations such as number formatting, string padding, file size conversion, and byte/hex transformations.

## Use Cases

- Display formatting and UI-friendly output
- Readable file size labels
- Data conversion between byte arrays and hex strings

## Features

- String padding (start and end)
- Number formatting with thousands separator
- Long string line wrapping
- File size formatting (e.g. `"1 MiB"`)
- Byte array and hex string conversion

## Usage

Padding strings:

```kotlin
val start = AppText.completeStart("3", "000") // "003"
val end = AppText.completeEnd("3", "###")     // "3##"
```

Format numbers with thousands separator:

```kotlin
val formatted = AppText.formatNumber(1234567.89)
// "1,234,567.89"
```

Wrap long strings into lines:

```kotlin
val wrapped = AppText.wrapString("1234567890123456789", 7)
/*
1234567
8901234
56789
*/
```

The `length` parameter controls the maximum characters per line. You can customize the line separator if needed.

Format file sizes:

```kotlin
val readable = AppText.formatFileSize(1048576)
// "1 MiB"
```

Byte array and hex string conversion:

```kotlin
val hex = AppText.byteArrayToString(byteArrayOf(0x1F, 0x2B)) // "1f2b"
val bytes = AppText.stringToByteArray("1f2b")                // [0x1F, 0x2B]
```

## Source Code

[AppText.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppText.kt)