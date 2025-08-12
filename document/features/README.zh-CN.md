多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# 功能列表

AndroidWidget 提供的工具模块目录。各模块均有独立 README，包含用法说明与源码链接。

## 1. 应用与 Activity 管理

#### [ApplicationHolder](./ApplicationHolder)
全局访问 `Application` 实例、调试状态、版本与包信息。通过 Jetpack Startup 自动初始化。

#### [ActivityHolder](./ActivityHolder)
管理所有活动的 `Activity` 实例，获取顶部 Activity，为每个 Activity 存储临时数据，访问 Activity 列表。

## 2. Activity 结果与权限处理

#### [ActivityResult / IntentLauncher](./ActivityResult)
简化 `startActivityForResult` 流程，支持链式 `.onSuccess`、`.onFailure`、`.onResult` 处理。自动生命周期管理，支持并发启动。

#### [AppPermission](./AppPermission)
简化运行时权限请求，一次调用处理多个权限，仅请求未授权的权限。`onGranted` 处理全部授权，`onResult` 返回详细权限状态。

## 3. 数据存储、序列化与安全

#### [AppData](./AppData)
基于 Jetpack DataStore 的现代化键值存储。提供协程与同步 API，支持 `Flow` 观察，可创建多个命名实例。

#### [AppGson](./AppGson)
增强版 Gson 工具，自动过滤 null 并支持泛型反序列化。可自定义 Gson 实例。

#### [AppEncrypt](./AppEncrypt)
字符串与流的 AES 和 RSA 加解密工具。支持 RSA 密钥对生成与解析。

#### [AppMessageDigest](./AppMessageDigest)
支持可选进度回调的字符串与流 MD5 哈希。适用于文件校验和大文件处理。

#### [AppRandom](./AppRandom)
生成随机字符串、数字与打乱的列表。支持指定范围整数和种子复现。

## 4. 界面工具与用户交互

#### [AppDensity](./AppDensity)
在 `px`、`dp`、`sp` 单位之间转换。支持自定义 `DisplayMetrics`。

#### [AppKeyboard](./AppKeyboard)
显示/隐藏软键盘，并支持点击空白区域自动收起。

#### [AppLog](./AppLog)
增强型日志工具，支持线程信息、调用位置与可复用日志实例。

#### [AppSpan](./AppSpan)
简化 `SpannableString` 样式的创建与应用。链式 API 支持文字颜色、背景、下划线、可点击区域等。

#### [AppSystem](./AppSystem)
获取电池、网络、屏幕与进程信息。

#### [AppText](./AppText)
字符串格式化、文件大小转换、字节/十六进制转换。

#### [AppTime](./AppTime)
根据自定义模式、时区和地区格式化时间戳与时长。

#### [AppToast](./AppToast)
线程安全地显示 Toast，自动切换到主线程，忽略空白消息。

#### [AppView](./AppView)
安全点击、可见性控制、View 数据存储、`MeasureSpec` 工具。

## 5. 文件操作

#### [AppZip](./AppZip)
压缩与解压文件/目录，保持原目录结构。支持 UTF-8 编码。

## 6. 动画与 View 装饰

#### [BreatheInterpolator](./BreatheInterpolator)
呼吸灯风格动画插值器，支持自下而上或自上而下模式。

#### [GridItemDecoration](./GridItemDecoration)
RecyclerView 网格布局的间距装饰器，支持水平与垂直布局。

#### [LinearItemDecoration](./LinearItemDecoration)
RecyclerView 线性布局的间距装饰器，支持彩色分隔线与内边距。

## 7. 协程与事件

#### [CoroutinesHolder](./CoroutinesHolder)
全局协程作用域（`Default`、`Main`、`IO`）便于统一启动。

#### [AppEvent](./AppEvent)
基于 `Flow` 的全局事件总线，支持生命周期感知的跨组件通信。非粘性分发、强类型或 `CommonEvent` 通用事件、`post` / `postAsync` 双模式发送。

## 8. 多语言管理

#### [String Resource Manager](./StringResource)
模块化、模板化的多语言字符串资源管理。集中注册与获取，适合多模块项目。