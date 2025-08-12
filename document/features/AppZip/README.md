Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppZip

## Introduction

`AppZip` provides file compression and decompression utilities. It supports single-file compression, multi-file/directory compression, and zip file extraction while preserving directory structure.

## Features

- Compress a single file into a zip entry
- Compress multiple files or directories with structure preserved
- Extract zip files to a target directory
- UTF-8 encoding for zip entry names

## Usage

Compress a single file:

```kotlin
val inputFile = File("example.txt")
val outputFile = File("example.zip")

AppZip.zipFile("example.txt", inputFile.inputStream(), outputFile.outputStream())
```

Compress multiple files or directories:

```kotlin
AppZip.zipFiles(
    File("output.zip").outputStream(),
    File("file1.txt"),
    File("dir2")
)
```

Extract a zip file:

```kotlin
AppZip.unzipFile(
    File("example.zip").inputStream(),
    File("output_directory")
)
```

## Notes

- Empty directories are preserved during compression.
- File names are stored using UTF-8 encoding (default for `ZipEntry` in modern Java).
- Throws an exception if zip entries are not UTF-8 encoded.

## Source Code

[AppZip.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppZip.kt)