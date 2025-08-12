多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppRandom

## 简介

`AppRandom` 提供随机字符串、整数和打乱列表的便捷方法。基于当前系统时间生成种子，既保证随机性，也支持按需复现。

## 场景

- 生成测试数据
- 创建唯一标识符
- 随机化用户体验

## 功能

- 可配置长度的随机字母数字字符串
- 指定范围内的随机整数
- 集合洗牌（不修改原列表）

## 使用方式

生成随机字符串（数字、大小写字母）：

```kotlin
val str = AppRandom.randomString(12)
// 示例: "aZ3bD9x1YqT8"
```

生成范围内随机整数：

```kotlin
val number = AppRandom.randomInt(10..99)
// 示例: 47
```

范围为空时返回 `0`。

打乱列表：

```kotlin
val list = listOf(1, 2, 3, 4, 5)
val shuffled = AppRandom.shuffleList(list)
// 示例: [3, 1, 5, 2, 4]
```

## 源码链接

[AppRandom.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppRandom.kt)