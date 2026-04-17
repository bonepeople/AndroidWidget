多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppPermission

## 简介

`AppPermission` 提供同步的运行时权限检查和基于协程的权限申请。权限申请内部使用 AndroidX `ActivityResultContract`，执行 `request()` 时立即发起申请，并直接返回结果，无需回调链或临时变量。

## 功能

- 同步查询单个权限的当前状态
- 在协程中申请多个权限
- 申请后区分普通拒绝和请求受阻
- 使用 AndroidX `ActivityResultContracts.RequestMultiplePermissions` 发起系统权限流程
- 调用 `request()` 即发起申请，无需先注册回调再单独触发
- 仅请求尚未授权的权限
- 同时返回是否全部授权及每项权限的状态

## 使用方式

导入新版权限工具：

```kotlin
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.util.permission.AppPermission
import com.bonepeople.android.widget.util.permission.PermissionStatus
import kotlinx.coroutines.launch
```

检查多个权限是否全部授权：

```kotlin
val permissions = arrayOf(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.ACCESS_COARSE_LOCATION
)
if (permissions.all { AppPermission.checkStatus(it).isGranted() }) {
    // 所有权限均已授权
}
```

查询单个权限状态：

```kotlin
when (AppPermission.checkStatus(android.Manifest.permission.CAMERA)) {
    PermissionStatus.GRANTED -> openCamera()
    PermissionStatus.NOT_REQUESTED -> showFirstRequestHint()
    PermissionStatus.DENIED -> requestPermission()
    PermissionStatus.REQUEST_BLOCKED -> openAppSettings()
}
```

如果只需判断单个状态是否已授权，可以使用 `PermissionStatus.isGranted()`：

```kotlin
if (AppPermission.checkStatus(android.Manifest.permission.CAMERA).isGranted()) {
    openCamera()
}
```

在生命周期感知的协程中申请权限：

```kotlin
lifecycleScope.launch {
    val result = AppPermission.request(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (result.allGranted()) {
        startFeature()
    }

    val cameraStatus = result.permissionStatuses[android.Manifest.permission.CAMERA]
    if (cameraStatus == PermissionStatus.REQUEST_BLOCKED) {
        openAppSettings()
    }
}
```

## 从回调 API 迁移

将旧版回调链：

```kotlin
com.bonepeople.android.widget.util.AppPermission.request(permission)
    .onResult { allGranted, permissionResult ->
        if (allGranted) startFeature()
        val granted = permissionResult[permission] == true
    }
```

替换为协程 API：

```kotlin
lifecycleScope.launch {
    val result = AppPermission.request(permission)
    if (result.allGranted()) startFeature()
    val status = result.permissionStatuses[permission]
}
```

## 注意事项

- 可以用 `permissions.all { AppPermission.checkStatus(it).isGranted() }` 检查多个权限；空权限列表的 `all` 结果为 `true`。
- `request()` 是挂起函数，必须在协程中调用，推荐使用 `lifecycleScope` 等生命周期感知的作用域。
- `request()` 会跳过已经授权的权限，但结果仍按原始顺序包含传入的全部权限。
- 权限被拒绝后，`request()` 通过 `ActivityCompat.shouldShowRequestPermissionRationale()` 判断普通申请流程是否受阻；受阻时返回 `REQUEST_BLOCKED`。
- `REQUEST_BLOCKED` 表示普通权限申请流程当前受阻，常见于用户选择“不再询问”或多次拒绝权限；该状态不用于判断具体的受阻原因。
- 旧版回调 API 仍保留在 `com.bonepeople.android.widget.util.AppPermission`，新代码应使用本文介绍的新版 API。
- `NOT_REQUESTED` 的检测当前尚未实现，因此未授权的权限暂时返回 `DENIED`。

## 源码链接

- [AppPermission.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/AppPermission.kt)
- [PermissionResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/PermissionResult.kt)
- [PermissionStatus.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/PermissionStatus.kt)