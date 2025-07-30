Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AndroidWidget Documentation

AndroidWidget is a practical utility library for everyday Android development. It wraps recurring tasks — JSON parsing, permission requests, Activity result callbacks, View interactions, system bar adaptation — into concise APIs so you write less boilerplate and focus on business logic.

Built from years of Android development experience and refined with community best practices. Components are auto-initialized via Jetpack App Startup, so **they work out of the box** after integration with no tedious setup.

---

## Why AndroidWidget

| Pain Point | AndroidWidget Approach |
|------------|------------------------|
| No `Context` or `Activity` available in utils, services, or SDK callbacks | `ApplicationHolder` & `ActivityHolder` — auto-initialized, access from anywhere |
| Gson null-safety issues, TypeToken boilerplate, permission & Activity result ceremony | `AppGson`, `AppPermission`, `Intent.launch()` — concise one-liner APIs |
| `SharedPreferences` lacks coroutine support and reactive updates | `AppData` — DataStore-based storage with sync/async APIs and `Flow` observation |
| EventBus-style solutions cause lifecycle leaks or require manual unregister | `AppEvent` — lifecycle-bound subscriptions, auto-cancelled on destroy |
| Repetitive View clicks, visibility, rich text, Toast, and inset adaptation on every screen | `AppView`, `AppSpan`, `AppToast`, `InsetsLayout` — ready-to-use extensions and layouts |
| Debug logs missing caller location, hard to trace in large codebases | `AppLog` — built-in thread info and stack trace with one toggle |

---

## Highlighted Features

The features below are selected for their day-to-day impact — less boilerplate, fewer bugs, faster iteration. This is not an exhaustive list; see [features/README.md](features/README.md) for everything available.

### ApplicationHolder & ActivityHolder — Global Context, Zero Setup

Utility classes, background tasks, and third-party SDK callbacks often need a `Context` or the current `Activity`, but there's nowhere to get one. These two holders are **auto-initialized via Jetpack Startup** — no manual registration, no `Context` parameter threading:

```kotlin
// Application & debug info — call from anywhere
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val version = ApplicationHolder.getVersionName()

// Current Activity — dialogs, deep links, permission callbacks
val activity = ActivityHolder.getTopActivity()
```

`ActivityHolder` also tracks all active activities and supports per-activity temporary data storage — handy for cross-page communication and cleanup.

👉 Full docs: [ApplicationHolder](features/ApplicationHolder/README.md) · [ActivityHolder](features/ActivityHolder/README.md)

---

### AppGson — JSON Conversion Without Type Tokens or null Headaches

Network requests and local caching rely on JSON serialization. Raw Gson requires manual type specification, and `null` values in JSON force you to guard every field in Kotlin.

`AppGson` simplifies it to a single line:

```kotlin
val json = AppGson.toJson(user)
val user: User = AppGson.toObject(json)  // no TypeToken needed
```

The default Gson instance includes a null-filtering adapter that ignores null fields during deserialization, reducing NPE risk.

👉 Full docs: [AppGson](features/AppGson/README.md)

---

### Intent.launch — Activity Navigation and Result Callbacks, Chained

Still writing `registerForActivityResult`, holding Launcher references, and branching in callbacks? `Intent.launch()` compresses the entire flow into a chain:

```kotlin
Intent(this, DetailActivity::class.java)
    .launch()
    .onSuccess { data -> handleResult(data) }
    .onFailure { result -> handleCancel(result) }
```

No manual registration, automatic lifecycle management, and support for concurrent launches.

👉 Full docs: [ActivityResult / IntentLauncher](features/ActivityResult/README.md)

---

### AppPermission — Permission Requests That Fire on Call

Many permission libraries require registering a callback first, then triggering the request separately. `AppPermission` starts the flow **the moment you call `request()`**:

```kotlin
AppPermission.request(Manifest.permission.CAMERA)
    .onGranted { openCamera() }
    .onResult { allGranted, resultMap ->
        if (!allGranted) showDeniedHint(resultMap)
    }
```

Already-granted permissions are filtered out automatically — no redundant dialogs.

👉 Full docs: [AppPermission](features/AppPermission/README.md)

---

### AppData — Modern Key-Value Storage, SharedPreferences Done Right

Tired of `SharedPreferences`' blocking API and lack of reactive updates? `AppData` wraps Jetpack DataStore with both sync and coroutine APIs, plus `Flow` observation out of the box:

```kotlin
val data = AppData.default

// Write & read — sync or suspend, your choice
data.putStringSync("token", token)
val token = data.getStringSync("token", "")

// React to changes — no manual listeners
data.getStringFlow("token").collect { updateUI(it) }
```

Create multiple named stores for different modules or users — `AppData.create("settings")`.

👉 Full docs: [AppData](features/AppData/README.md)

---

### AppEvent — Lifecycle-Safe Event Bus, No Leaks

Global event delivery often means sticky events, manual unregister, or callbacks firing after `Activity` is destroyed. `AppEvent` is built on `Flow` with lifecycle binding — **subscribe once, forget about cleanup**:

```kotlin
// Subscribe — auto-cancelled when Activity is destroyed
AppEvent.register(this) { event ->
    when (event) {
        is LoginSuccessEvent -> refreshProfile()
        is CommonEvent -> if (event.name == "refresh") reloadData()
    }
}

// Post from anywhere — even non-coroutine callbacks
AppEvent.postAsync(LoginSuccessEvent(user))
```

Non-sticky by default, typed events supported, main-thread delivery guaranteed.

👉 Full docs: [AppEvent](features/AppEvent/README.md)

---

### AppView — View Extensions for Everyday UI Interactions

Debounced clicks, visibility toggles, temporary data on Views — operations that appear on nearly every screen:

```kotlin
button.singleClick { submitOrder() }
loadingView.show()
errorView.gone()
emptyView.switchShow(list.isEmpty())
view.putExtra("position", index)
```

Also includes `MeasureSpec` parsing helpers for custom View development.

👉 Full docs: [AppView](features/AppView/README.md)

---

### AppSpan — Rich Text Without Spannable Boilerplate

Building styled text with `SpannableString` means juggling span classes, start/end indices, and flags. `AppSpan` offers a chainable API that reads like the output:

```kotlin
textView.text = AppSpan()
    .textColor("Only ", Color.GRAY)
    .textColor("today", Color.RED)
    .textSize(" — 50% off", 12f)
```

Works anywhere `CharSequence` is accepted — `TextView`, `Toast`, `Snackbar`, and more.

👉 Full docs: [AppSpan](features/AppSpan/README.md)

---

### AppToast — Show Toasts From Any Thread

Calling `Toast.makeText()` off the main thread crashes. `AppToast` handles thread switching automatically and silently ignores blank messages:

```kotlin
// Safe from IO threads, callbacks, or coroutines
CoroutinesHolder.io.launch {
    val file = downloadReport()
    AppToast.show("Saved to $file")
}
```

One line, no `runOnUiThread`, no null checks.

👉 Full docs: [AppToast](features/AppToast/README.md)

---

### InsetsLayout — System Bar & Keyboard Insets, One Layout Handles It All

Adapting to status bars, navigation bars, and the soft keyboard usually means repetitive `WindowInsets` listeners in every Activity. `InsetsLayout` wraps it into a `ConstraintLayout` — **specify edges in XML, padding adapts automatically**:

```xml
<com.bonepeople.android.widget.view.InsetsLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:insets="top|bottom">

    <!-- child views -->
</com.bonepeople.android.widget.view.InsetsLayout>
```

Powered by `WindowInsetsCompat` (system bars + IME), responds dynamically to keyboard show/hide, and layers on top of existing padding.

---

### AppLog — Debug Logs That Tell You Where They Came From

Standard `Log.d()` output doesn't show which thread or line triggered the log — tracing issues in large projects becomes guesswork. `AppLog` adds caller location and thread info with a single toggle:

```kotlin
val log = AppLog.tag("Network")
log.showStackInfo = true
log.debug("request finished")
// → [main] request finished @ ApiService.fetch:42
```

Per-tag instances, global enable/disable switch, five log levels — a drop-in upgrade over raw `Log`.

👉 Full docs: [AppLog](features/AppLog/README.md)

---

## Quick Start

### 1. Add Dependency

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

// module build.gradle
dependencies {
    implementation 'com.github.bonepeople:AndroidWidget:1.7.4'
}
```

### 2. Initialization (Usually Not Required)

The library uses Jetpack App Startup to initialize on app launch. **No extra code is needed by default.**

For multi-process apps, call this early in `Application.onCreate`:

```kotlin
import androidx.startup.StartupHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StartupHelper.initializeAll(this)
    }
}
```

> Repeated calls won't cause double initialization. Most components use lazy loading and won't affect startup speed.

---

## Explore More Features

The highlights above cover the most impactful day-to-day capabilities. The full library also includes:

- Encryption & hashing (AppEncrypt, AppMessageDigest)
- File compression (AppZip)
- Time & text formatting (AppTime, AppText, AppDensity)
- RecyclerView decorators (LinearItemDecoration, GridItemDecoration)
- Global coroutine scopes (CoroutinesHolder)
- Keyboard control (AppKeyboard)
- System info queries (AppSystem)
- Multi-language resource management (String Resource Manager)
- And more …

👉 **Browse all features**: [features/README.md](features/README.md) ([中文](features/README.zh-CN.md) | [Español](features/README.es-ES.md))

Each feature module has its own usage guide and code examples — consult as needed.

---

## Sample Project

The `sample` module in this repository demonstrates typical usage of Intent launching, permission requests, Gson conversion, and more:

```
sample/src/main/java/com/bonepeople/android/widget/sample/
```

---

## Links

- Repository: https://github.com/bonepeople/AndroidWidget
- Releases: latest tags via [JitPack](https://jitpack.io/#bonepeople/AndroidWidget)