Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppRandom

## Introduction

`AppRandom` provides convenient methods for generating random strings, integers, and shuffled lists. It uses a seed based on the current system time, ensuring randomness while allowing reproducibility when needed.

## Use Cases

- Generate test data
- Create unique identifiers
- Randomize user experiences

## Features

- Random alphanumeric strings of configurable length
- Random integers within a specified range
- Shuffled copies of collections (original list is not modified)

## Usage

Generate a random string (digits, lowercase and uppercase letters):

```kotlin
val str = AppRandom.randomString(12)
// Example: "aZ3bD9x1YqT8"
```

Generate a random integer in a range:

```kotlin
val number = AppRandom.randomInt(10..99)
// Example: 47
```

Returns `0` if the range is empty.

Shuffle a list:

```kotlin
val list = listOf(1, 2, 3, 4, 5)
val shuffled = AppRandom.shuffleList(list)
// Example: [3, 1, 5, 2, 4]
```

## Source Code

[AppRandom.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppRandom.kt)