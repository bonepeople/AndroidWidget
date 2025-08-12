Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppMessageDigest

## Introduction

`AppMessageDigest` computes MD5 hashes from strings and input streams. It supports progress tracking during stream processing, making it suitable for file integrity checks and content fingerprinting.

## Use Cases

- Validate downloaded files using MD5 hashes
- Create content fingerprints or deduplicate strings
- Show progress while hashing large files

## Features

- MD5 hash from string content
- MD5 hash from `InputStream` with chunked reading
- Progress callback for bytes processed
- Customizable buffer size for stream reading

## Usage

Hash a string:

```kotlin
val hash = AppMessageDigest.md5("Hello, World!")
// Output: fc3ff98e8c6a0d3087d515c0473f8677
```

Hash a file with progress feedback:

```kotlin
val file = File("/path/to/large_file.zip")
val md5 = AppMessageDigest.md5(file.inputStream(), 4096) { bytesRead ->
    Log.d("Progress", "Read $bytesRead bytes")
}
```

## Notes

- MD5 is not a secure encryption method — do not use it for sensitive data.
- Streams are not closed automatically — close them manually.
- Interrupt support is not implemented in this version.

## Source Code

[AppMessageDigest.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppMessageDigest.kt)