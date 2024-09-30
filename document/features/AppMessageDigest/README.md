Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppMessageDigest Usage Guide

> This document was generated with the help of ChatGPT  
> Source code link: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppMessageDigest.kt

## Introduction

`AppMessageDigest` is a utility class for computing MD5 hashes from strings and input streams. It's designed for file integrity checks, hash-based comparisons, and content fingerprinting. It also supports progress tracking during stream processing.

## Features

- Compute MD5 hash from string content;
- Compute MD5 from `InputStream` in chunks;
- Progress callback for tracking bytes processed;
- Customizable buffer size for stream reading.

## How to Use

### 1. Hash a string using MD5

```kotlin
val hash = AppMessageDigest.md5("Hello, World!")
// Output: fc3ff98e8c6a0d3087d515c0473f8677
```

### 2. Hash a file with progress feedback

```kotlin
val file = File("/path/to/large_file.zip")
val md5 = AppMessageDigest.md5(file.inputStream(), 4096) { bytesRead ->
    Log.d("Progress", "Read $bytesRead bytes")
}
```

### 3. Custom read logic (used internally)

The utility uses `readStream` to handle chunked reading, updates, and progress reporting. You typically don’t need to call it directly.

## Recommended Use Cases

- Validate downloaded files using MD5 hashes;
- Create content fingerprints or deduplicate strings;
- Show progress while hashing large files.

## Notes

- MD5 is not a secure encryption method—don’t use for sensitive data;
- Streams are not closed automatically—ensure you close them manually;
- Interrupt support is not implemented in this version.