Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# ApplicationHolder

**Link to source code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ApplicationHolder.kt

`ApplicationHolder` is a centralized utility for accessing key application-level information and the `Application` instance across your Android app.

It is automatically initialized via Jetpack Startup, making it convenient for global access.

> 📄 This documentation was assisted by ChatGPT.

## Usage

This utility is initialized automatically through the AndroidX Startup mechanism. After initialization, you can safely use it from anywhere in your application.

### Example

```kotlin
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val versionName = ApplicationHolder.getVersionName()
val versionCode = ApplicationHolder.getVersionCode()
val packageName = ApplicationHolder.getPackageName()
```

### Typical Output

- `debug`: `true` if the app is built in debug mode.
- `getVersionName()`: returns the app’s version name (e.g., `"1.0.0"`).
- `getVersionCode()`: returns the app’s version code (e.g., `10000`).

## Notes

- If `ApplicationHolder` is accessed before initialization, an exception will be thrown.