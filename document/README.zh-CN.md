多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AndroidWidget 项目文档

AndroidWidget 是一套面向 Android 日常开发的实用工具库。它把开发中反复出现的 JSON 解析、权限申请、Activity 结果回调、View 交互、系统栏适配等能力封装成简洁 API，让你少写样板代码，把精力放在业务逻辑上。

项目基于多年 Android 开发经验整理而成，并结合社区优秀实践持续迭代。组件通过 Jetpack App Startup 自动初始化，**接入后默认即可使用**，无需繁琐配置。

---

## 为什么选择 AndroidWidget

| 痛点 | AndroidWidget 的做法 |
|------|----------------------|
| 工具类、Service、SDK 回调里拿不到 `Context` 或 `Activity` | `ApplicationHolder` & `ActivityHolder` — 自动初始化，随处可用 |
| Gson 空安全、TypeToken 样板代码，权限和 Activity 结果回调流程繁琐 | `AppGson`、`AppPermission`、`Intent.launch()` — 一行式 API |
| `SharedPreferences` 不支持协程，也无法响应式监听变化 | `AppData` — 基于 DataStore，提供 sync/async API 与 `Flow` 观察 |
| EventBus 类方案容易泄漏，或需要手动反注册 | `AppEvent` — 绑定生命周期，销毁时自动取消订阅 |
| 每个页面重复写点击防抖、显隐、富文本、Toast、系统栏适配 | `AppView`、`AppSpan`、`AppToast`、`InsetsLayout` — 扩展函数和布局开箱即用 |
| 调试日志缺少调用位置，大项目里难以追踪 | `AppLog` — 一行开关即可输出线程与调用栈信息 |

---

## 亮点功能

以下功能按日常开发中的实际收益选取——更少样板代码、更少 bug、更快迭代。这里并非完整列表，全部功能请查看 [features/README.zh-CN.md](features/README.zh-CN.md)。

### ApplicationHolder & ActivityHolder — 全局 Context，零配置

工具类、后台任务、第三方 SDK 回调常常需要 `Context` 或当前 `Activity`，却没有合适的来源。这两个 Holder 通过 **Jetpack Startup 自动初始化**，无需手动注册，也不用在方法间层层传递 `Context`：

```kotlin
// 随处获取 Application 与调试信息
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val version = ApplicationHolder.getVersionName()

// 获取当前 Activity — 弹窗、Deep Link、权限回调等场景
val activity = ActivityHolder.getTopActivity()
```

`ActivityHolder` 还维护所有活动 Activity 列表，支持按 Activity 存储临时数据，便于跨页面通信与清理。

👉 详细文档：[ApplicationHolder](features/ApplicationHolder/README.zh-CN.md) · [ActivityHolder](features/ActivityHolder/README.zh-CN.md)

---

### AppGson — JSON 转换，告别 TypeToken 与 null 烦恼

网络请求和本地缓存离不开 JSON 序列化。原生 Gson 需要手动指定类型，JSON 中的 `null` 还会让 Kotlin 空安全处处设防。

`AppGson` 把这一切简化到一行：

```kotlin
val json = AppGson.toJson(user)
val user: User = AppGson.toObject(json)  // 无需 TypeToken
```

默认 Gson 实例内置 null 过滤适配器，反序列化时自动忽略 null 字段，降低 NPE 风险。

👉 详细文档：[AppGson](features/AppGson/README.zh-CN.md)

---

### Intent.launch — Activity 跳转与结果回调，链式写法

还在手写 `registerForActivityResult`、维护 Launcher 引用、在回调里分支判断？`Intent.launch()` 把整套流程压缩成链式调用：

```kotlin
Intent(this, DetailActivity::class.java)
    .launch()
    .onSuccess { data -> handleResult(data) }
    .onFailure { result -> handleCancel(result) }
```

无需手动注册，生命周期自动管理，支持并发启动多个页面。

👉 详细文档：[ActivityResult / IntentLauncher](features/ActivityResult/README.zh-CN.md)

---

### AppPermission — 权限申请，调用即请求

很多权限库要求先注册回调、再触发请求，步骤割裂。`AppPermission` 在 **调用 `request()` 的瞬间即发起申请**：

```kotlin
AppPermission.request(Manifest.permission.CAMERA)
    .onGranted { openCamera() }
    .onResult { allGranted, resultMap ->
        if (!allGranted) showDeniedHint(resultMap)
    }
```

已授权的权限会自动过滤，避免重复弹窗。

👉 详细文档：[AppPermission](features/AppPermission/README.zh-CN.md)

---

### AppData — 现代化键值存储，SharedPreferences 的升级版

受够了 `SharedPreferences` 的阻塞 API 和无法响应式监听？`AppData` 封装 Jetpack DataStore，同步/协程 API 兼备，原生支持 `Flow` 观察：

```kotlin
val data = AppData.default

// 读写 — sync 或 suspend，按需选择
data.putStringSync("token", token)
val token = data.getStringSync("token", "")

// 响应式监听 — 无需手动注册 Listener
data.getStringFlow("token").collect { updateUI(it) }
```

可按模块或用户创建多个命名存储：`AppData.create("settings")`。

👉 详细文档：[AppData](features/AppData/README.zh-CN.md)

---

### AppEvent — 生命周期安全的事件总线，不泄漏

全局事件分发常伴随粘性事件、手动反注册，或在 Activity 销毁后仍触发回调。`AppEvent` 基于 `Flow` 并绑定生命周期 —— **订阅一次，无需关心清理**：

```kotlin
// 订阅 — Activity 销毁时自动取消
AppEvent.register(this) { event ->
    when (event) {
        is LoginSuccessEvent -> refreshProfile()
        is CommonEvent -> if (event.name == "refresh") reloadData()
    }
}

// 任意位置发送 — 包括非协程回调
AppEvent.postAsync(LoginSuccessEvent(user))
```

默认非粘性，支持强类型事件，回调保证在主线程执行。

👉 详细文档：[AppEvent](features/AppEvent/README.zh-CN.md)

---

### AppView — View 扩展，日常交互一行搞定

防重复点击、显隐切换、临时数据挂载——几乎每个页面都会遇到的操作：

```kotlin
button.singleClick { submitOrder() }
loadingView.show()
errorView.gone()
emptyView.switchShow(list.isEmpty())
view.putExtra("position", index)
```

还提供 `MeasureSpec` 解析辅助，自定义 View 开发也能少写几行。

👉 详细文档：[AppView](features/AppView/README.zh-CN.md)

---

### AppSpan — 富文本，告别 Spannable 样板代码

用 `SpannableString` 拼样式需要处理 span 类、起止索引和 flag，代码冗长。`AppSpan` 提供链式 API，写法与最终效果一致：

```kotlin
textView.text = AppSpan()
    .textColor("仅限 ", Color.GRAY)
    .textColor("今日", Color.RED)
    .textSize(" — 5 折", 12f)
```

任何接受 `CharSequence` 的地方都能用 — `TextView`、`Toast`、`Snackbar` 等。

👉 详细文档：[AppSpan](features/AppSpan/README.zh-CN.md)

---

### AppToast — 任意线程安全弹出 Toast

在非主线程调用 `Toast.makeText()` 会崩溃。`AppToast` 自动切换主线程，并静默忽略空白消息：

```kotlin
// IO 线程、回调、协程中均可安全调用
CoroutinesHolder.io.launch {
    val file = downloadReport()
    AppToast.show("已保存至 $file")
}
```

一行代码，无需 `runOnUiThread`，无需判空。

👉 详细文档：[AppToast](features/AppToast/README.zh-CN.md)

---

### InsetsLayout — 系统栏与键盘 inset 适配，一个布局全搞定

适配状态栏、导航栏和软键盘，通常要在每个 Activity 里重复编写 `WindowInsets` 监听。`InsetsLayout` 将其封装进 `ConstraintLayout` —— **XML 中指定边，padding 自动适配**：

```xml
<com.bonepeople.android.widget.view.InsetsLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:insets="top|bottom">

    <!-- 子 View -->
</com.bonepeople.android.widget.view.InsetsLayout>
```

基于 `WindowInsetsCompat`（系统栏 + IME），动态响应键盘弹出/收起，在原有 padding 基础上叠加。

---

### AppLog — 能告诉你「从哪来」的调试日志

标准 `Log.d()` 不显示触发线程和代码行号，大项目里排查问题全靠猜。`AppLog` 一行开关即可输出调用位置与线程信息：

```kotlin
val log = AppLog.tag("Network")
log.showStackInfo = true
log.debug("request finished")
// → [main] request finished @ ApiService.fetch:42
```

按 tag 创建实例、全局开关、五个日志级别 — 原生 `Log` 的直接升级版。

👉 详细文档：[AppLog](features/AppLog/README.zh-CN.md)

---

## 快速接入

### 1. 添加依赖

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

// 模块 build.gradle
dependencies {
    implementation 'com.github.bonepeople:AndroidWidget:1.7.4'
}
```

### 2. 初始化（通常无需操作）

项目使用 Jetpack App Startup 在应用启动时自动完成初始化，**默认情况下不需要任何额外代码**。

多进程场景下，在 `Application.onCreate` 中提前调用：

```kotlin
import androidx.startup.StartupHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StartupHelper.initializeAll(this)
    }
}
```

> 重复调用不会导致多次初始化；大部分组件采用懒加载，不影响启动速度。

---

## 探索更多功能

上文亮点按日常开发收益选取，完整库还包含：

- 加密与哈希（AppEncrypt、AppMessageDigest）
- 文件压缩（AppZip）
- 时间与文本格式化（AppTime、AppText、AppDensity）
- RecyclerView 间距装饰（LinearItemDecoration、GridItemDecoration）
- 全局协程作用域（CoroutinesHolder）
- 键盘控制（AppKeyboard）
- 系统信息查询（AppSystem）
- 多语言资源管理（String Resource Manager）
- 以及更多 …

👉 **查看全部功能**：[features/README.zh-CN.md](features/README.zh-CN.md)（[English](features/README.md) | [Español](features/README.es-ES.md)）

每个功能模块目录下均有独立的使用说明与代码示例，按需查阅即可。

---

## 示例项目

仓库中的 `sample` 模块演示了 Intent 跳转、权限申请、Gson 转换等典型用法，可作为接入参考：

```
sample/src/main/java/com/bonepeople/android/widget/sample/
```

---

## 相关链接

- 项目仓库：https://github.com/bonepeople/AndroidWidget
- 发布版本：通过 [JitPack](https://jitpack.io/#bonepeople/AndroidWidget) 获取最新 tag