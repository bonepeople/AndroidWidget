Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppRandom Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppRandom.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppRandom` is a utility object that provides convenient methods for generating random values, including strings, integers, and shuffled lists. It uses a seed based on the current system time, ensuring randomness while allowing reproducibility if needed.

> This tool is ideal for generating test data, creating unique identifiers, or randomizing user experiences.

## How to Use

### 1. Generate a Random String

Creates a string composed of digits, lowercase and uppercase English letters:

```kotlin
val str = AppRandom.randomString(12)
// Example: "aZ3bD9x1YqT8"
```

- `length` specifies how many characters the result will have.

### 2. Generate a Random Integer in a Range

Returns a random integer between the specified range:

```kotlin
val number = AppRandom.randomInt(10..99)
// Example: 47
```

- If the range is empty, `0` is returned.

### 3. Shuffle a List

Returns a new shuffled version of the input collection:

```kotlin
val list = listOf(1, 2, 3, 4, 5)
val shuffled = AppRandom.shuffleList(list)
// Example: [3, 1, 5, 2, 4]
```

- The original list is not modified.