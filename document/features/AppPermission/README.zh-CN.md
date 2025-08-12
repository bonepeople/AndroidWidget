多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppPermission

## 简介

`AppPermission` 简化 Android 运行时权限的检查与请求。内部使用 `ActivityResultContract`，调用 `request()` 后立即触发请求流程。

## 功能

- 一次调用检查或请求多个权限
- 仅请求尚未授权的权限
- 全部授权时触发 `onGranted` 回调
- `onResult` 回调返回详细的权限状态映射

与其他框架不同，调用 `AppPermission.request()` 会立即触发请求流程，无需额外设置回调或调用单独的「申请」操作。

## 使用方式

检查权限：

```kotlin
AppPermission.check(android.Manifest.permission.CAMERA)
    .onGranted {
        // 所有请求的权限均已授权
    }
    .onResult { allGranted, resultMap ->
        // 按需处理结果映射
    }
```

请求权限：

```kotlin
AppPermission.request(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.READ_EXTERNAL_STORAGE
).onGranted {
    // 全部授权
}.onResult { allGranted, resultMap ->
    if (!allGranted) {
        // 处理被拒绝的权限
    }
}
```

## 注意事项

- `onGranted` 仅在**所有**请求的权限均被授权时触发。需要处理拒绝场景时请使用 `onResult`。

## 源码链接

[AppPermission.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt)