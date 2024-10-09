Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppZip Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppZip.kt  
**This document was generated with the help of ChatGPT.**

## Overview

`AppZip` provides file compression and decompression utilities.  
It supports single file compression, multi-file/directory compression, and zip file extraction.

## Features

### 1. Compress a Single File

```kotlin
val inputFile = File("example.txt")
val outputFile = File("example.zip")

AppZip.zipFile("example.txt", inputFile.inputStream(), outputFile.outputStream())
```

### 2. Compress Multiple Files or Directories

```kotlin
AppZip.zipFiles(
    File("output.zip").outputStream(),
    File("file1.txt"),
    File("dir2")
)
```

The directory structure is preserved in the resulting `.zip`.

### 3. Extract Zip File

```kotlin
AppZip.unzipFile(
    File("example.zip").inputStream(),
    File("output_directory")
)
```

**Note**: Throws an exception if the zip entries are not UTF-8 encoded.

## Notes

- Empty directories are preserved during compression.
- File names are stored using UTF-8 encoding (default for `ZipEntry` in modern Java).