多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# 功能列表

## 1. 应用与 Activity 管理

### [ApplicationHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ApplicationHolder)
- **用途**：全局访问 `Application` 实例、调试状态、版本与包信息。
- **特点**：通过 Jetpack Startup 自动初始化，无需手动配置。

### [ActivityHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityHolder)
- **用途**：管理所有活动的 `Activity` 实例，获取顶部 Activity，为每个 Activity 存储临时数据，访问 Activity 列表。
- **适用场景**：全局访问、跨 Activity 通信、生命周期清理。

---

## 2. Activity 结果与权限处理

### [ActivityResult / IntentLauncher](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityResult)
- **用途**：简化 `startActivityForResult` 流程，支持链式 `.onSuccess`、`.onFailure`、`.onResult` 处理。
- **特点**：自动生命周期管理，支持并发启动。

### [AppPermission](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppPermission)
- **用途**：简化运行时权限请求，一次调用处理多个权限，仅请求未授权的权限。
- **特点**：`onGranted` 回调处理全部授权，`onResult` 返回详细权限状态映射。

---

## 3. 数据存储、序列化与安全

### [AppData](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppData)
- **用途**：基于 Jetpack DataStore 的现代化键值存储。
- **特点**：提供协程与同步 API，支持 `Flow` 观察，可创建多个命名实例。

### [AppGson](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppGson)
- **用途**：增强版 Gson 工具，自动过滤 null 并支持泛型反序列化。
- **特点**：可自定义 Gson 实例，更好支持 Kotlin 空安全。

### [AppEncrypt](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppEncrypt)
- **用途**：字符串与流的 AES 和 RSA 加解密工具。
- **特点**：支持 RSA 密钥对生成与解析，默认算法安全可靠。

### [AppMessageDigest](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppMessageDigest)
- **用途**：支持可选进度回调的字符串与流 MD5 哈希。
- **适用场景**：文件校验，大文件处理。

### [AppRandom](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppRandom)
- **用途**：生成随机字符串、数字与打乱的列表。
- **特点**：支持指定范围整数，支持使用种子复现结果。

---

## 4. 界面工具与用户交互

### [AppDensity](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppDensity)
- **用途**：在 `px`、`dp`、`sp` 单位之间转换。
- **特点**：支持自定义 `DisplayMetrics`。

### [AppKeyboard](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppKeyboard)
- **用途**：显示/隐藏软键盘，并支持点击空白区域自动收起。

### [AppLog](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppLog)
- **用途**：增强型日志工具，支持线程信息、调用位置与可复用日志实例。

### [AppSpan](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppSpan)
- **用途**：简化 `SpannableString` 样式的创建与应用。
- **特点**：链式 API 支持文字颜色、背景、下划线、可点击区域等多种样式。

### [AppSystem](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppSystem)
- **用途**：获取电池、网络、屏幕与进程信息。

### [AppText](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppText)
- **用途**：字符串格式化、文件大小转换、字节/十六进制转换。

### [AppTime](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppTime)
- **用途**：根据自定义模式、时区和地区格式化时间戳与时长。

### [AppToast](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppToast)
- **用途**：线程安全地显示 Toast，自动切换到主线程，忽略空白消息。

### [AppView](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppView)
- **用途**：安全点击、可见性控制、View 数据存储、`MeasureSpec` 工具。

---

## 5. 文件操作

### [AppZip](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppZip)
- **用途**：压缩与解压文件/目录，保持原目录结构。
- **特点**：支持 UTF-8 编码。

---

## 6. 动画与 View 装饰

### [BreatheInterpolator](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/BreatheInterpolator)
- **用途**：呼吸灯风格动画插值器，支持自下而上或自上而下模式。

### [GridItemDecoration](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/GridItemDecoration)
- **用途**：RecyclerView 网格布局的间距装饰器，支持水平与垂直布局。

### [LinearItemDecoration](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/LinearItemDecoration)
- **用途**：RecyclerView 线性布局的间距装饰器，支持彩色分隔线与内边距。

---

## 7. 协程管理

### [CoroutinesHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/CoroutinesHolder)
- **用途**：全局协程作用域（`Default`、`Main`、`IO`）便于统一启动。

---

## 8. 多语言管理

### [String Resource Manager](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/StringResource)
- **用途**：模块化、模板化的多语言字符串资源管理。
- **特点**：集中注册与获取，适合多模块或多功能项目。

---

> 本文档由 ChatGPT 协助完成。