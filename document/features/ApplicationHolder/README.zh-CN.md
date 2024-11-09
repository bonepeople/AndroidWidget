多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# ApplicationHolder

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ApplicationHolder.kt

`ApplicationHolder` 是一个用于全局访问 `Application` 实例和应用关键信息的工具类。

它通过 Jetpack Startup 自动初始化，便于在项目中任何位置使用。

> 📄 本文档由 ChatGPT 协助完成。

## 使用方法

该工具类通过 AndroidX Startup 机制自动初始化，无需手动调用初始化方法。

初始化完成后，可直接使用以下方法获取应用信息：

### 示例代码

```kotlin
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val versionName = ApplicationHolder.getVersionName()
val versionCode = ApplicationHolder.getVersionCode()
val packageName = ApplicationHolder.getPackageName()
```

### 输出说明

- `debug`：如果当前是 Debug 模式，返回 `true`
- `getVersionName()`：返回应用版本名（如 `"1.0.0"`）
- `getVersionCode()`：返回应用版本号（如 `10000`）

## 注意事项

- 如果在初始化之前访问 `ApplicationHolder`，将会抛出异常。