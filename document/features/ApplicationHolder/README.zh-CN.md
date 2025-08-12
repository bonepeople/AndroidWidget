多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# ApplicationHolder

## 简介

`ApplicationHolder` 提供对 `Application` 实例及调试状态、版本号、包名等应用级信息的集中访问。

通过 Jetpack Startup 自动初始化，无需手动配置。

## 功能

- 访问全局 `Application` 上下文
- 判断应用是否处于调试模式
- 读取版本名、版本号和包名

## 使用方式

应用启动后，可在任意位置使用：

```kotlin
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val versionName = ApplicationHolder.getVersionName()
val versionCode = ApplicationHolder.getVersionCode()
val packageName = ApplicationHolder.getPackageName()
```

典型返回值：

- `debug`：调试模式下为 `true`
- `getVersionName()`：如 `"1.0.0"`
- `getVersionCode()`：如 `10000`

## 注意事项

- 在初始化完成前访问 `ApplicationHolder` 会抛出异常。

## 源码链接

[ApplicationHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ApplicationHolder.kt)