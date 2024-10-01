多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppPermission 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppPermission` 是一个用于 Android 应用运行时权限检查与请求的工具类。它使用 `ActivityResultContract` 简化权限处理流程。

> 本工具类适用于对权限逻辑要求不高的简单场景。
> - 可同时申请多个权限，内部会自动筛选出尚未授予的权限进行请求。
> - 与其他权限框架不同，调用 `AppPermission.request()` 方法即刻触发权限申请过程，无需显式设置回调或额外调用“确认”操作。
> - `onGranted` 回调仅在用户 **全部授权** 时触发，适合不需要处理权限拒绝情况的业务逻辑。

## 使用方法

### 1. 检查权限

使用 `check()` 方法检查权限是否已经授予：

```kotlin
val permission = AppPermission.check(android.Manifest.permission.CAMERA)
    .onGranted {
        // 所有请求的权限已被授予
    }
    .onResult { allGranted, resultMap ->
        // 可处理每项权限的结果
    }
```

### 2. 请求权限

请求用户授权权限：

```kotlin
val permission = AppPermission.request(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.READ_EXTERNAL_STORAGE
).onGranted {
    // 所有权限已被授予
}.onResult { allGranted, resultMap ->
    if (!allGranted) {
        // 处理未被授予的权限
    }
}
```