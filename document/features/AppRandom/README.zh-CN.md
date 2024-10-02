多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppRandom 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppRandom.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppRandom` 是一个提供多种随机数功能的工具类，包括生成随机字符串、随机整数、以及打乱列表顺序。它使用当前系统时间作为随机种子。

> 非常适合用于生成测试数据、构建唯一标识符，或实现具有随机性的用户体验。

## 使用方法

### 1. 生成随机字符串

生成包含数字、大小写英文字母的随机字符串：

```kotlin
val str = AppRandom.randomString(12)
// 示例：aZ3bD9x1YqT8
```

- 参数 `length` 表示字符串长度。

### 2. 生成指定范围内的随机整数

返回一个位于指定范围内的随机整数：

```kotlin
val num = AppRandom.randomInt(10..99)
// 示例：47
```

- 若范围为空，返回 `0`。

### 3. 打乱列表顺序

返回一个新列表，包含原列表元素的随机顺序排列：

```kotlin
val list = listOf(1, 2, 3, 4, 5)
val shuffled = AppRandom.shuffleList(list)
// 示例：[3, 1, 5, 2, 4]
```

- 原始列表不会被修改。