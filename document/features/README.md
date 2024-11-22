Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# Features List

## 1. Application & Activity Management

### [ApplicationHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ApplicationHolder)
- **Purpose**: Global access to the `Application` instance, debug status, version, and package information.
- **Features**: Automatically initialized via Jetpack Startup, no manual setup required.

### [ActivityHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityHolder)
- **Purpose**: Manage all active `Activity` instances, retrieve the top activity, store temporary data per activity, and access the activity list.
- **Use Cases**: Global access, cross-activity communication, lifecycle cleanup.

---

## 2. Activity Result & Permission Handling

### [ActivityResult / IntentLauncher](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityResult)
- **Purpose**: Simplify the `startActivityForResult` workflow with chainable `.onSuccess`, `.onFailure`, `.onResult` handlers.
- **Features**: Automatic lifecycle management, supports concurrent launches.

### [AppPermission](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppPermission)
- **Purpose**: Simplify runtime permission requests by handling multiple permissions in one call, only requesting ungranted ones.
- **Features**: `onGranted` callback for full grants, `onResult` map for detailed permission states.

---

## 3. Data Storage, Serialization & Security

### [AppData](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppData)
- **Purpose**: Modern key-value storage based on Jetpack DataStore.
- **Features**: Coroutine and synchronous APIs, `Flow` observation, multiple named instances.

### [AppGson](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppGson)
- **Purpose**: Enhanced Gson utility with automatic null filtering and generic deserialization.
- **Features**: Customizable Gson instance, improved Kotlin null-safety.

### [AppEncrypt](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppEncrypt)
- **Purpose**: AES and RSA encryption/decryption utility for strings and streams.
- **Features**: RSA key pair generation and parsing, secure default algorithms.

### [AppMessageDigest](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppMessageDigest)
- **Purpose**: MD5 hashing for strings and streams with optional progress tracking.
- **Use Cases**: File verification, large file processing.

### [AppRandom](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppRandom)
- **Purpose**: Generate random strings, numbers, and shuffled lists.
- **Features**: Range-based integers, reproducible randomness with seeds.

---

## 4. UI Tools & User Interaction

### [AppDensity](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppDensity)
- **Purpose**: Convert between `px`, `dp`, and `sp` units.
- **Features**: Supports custom `DisplayMetrics`.

### [AppKeyboard](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppKeyboard)
- **Purpose**: Show/hide the software keyboard and automatically dismiss on outside touch.

### [AppLog](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppLog)
- **Purpose**: Enhanced logging with thread info, caller location, and reusable log instances.

### [AppSpan](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppSpan)
- **Purpose**: Simplify the creation and application of `SpannableString` styles.
- **Features**: Chainable API for text color, background, underline, clickable spans, and more.

### [AppSystem](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppSystem)
- **Purpose**: Retrieve battery, network, screen, and process information.

### [AppText](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppText)
- **Purpose**: String formatting, file size conversion, byte/hex transformations.

### [AppTime](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppTime)
- **Purpose**: Format timestamps and durations with custom patterns, time zones, and locales.

### [AppToast](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppToast)
- **Purpose**: Thread-safe toast display, automatically switches to the main thread, ignores blank messages.

### [AppView](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppView)
- **Purpose**: Safe click handling, visibility control, per-view data storage, `MeasureSpec` utilities.

---

## 5. File Operations

### [AppZip](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppZip)
- **Purpose**: Compress and decompress files and directories while preserving structure.
- **Features**: UTF-8 encoding support.

---

## 6. Animation & View Decoration

### [BreatheInterpolator](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/BreatheInterpolator)
- **Purpose**: Breathing-style animation interpolator with bottom-to-top or top-to-bottom modes.

### [GridItemDecoration](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/GridItemDecoration)
- **Purpose**: RecyclerView grid item spacing decorator for both vertical and horizontal layouts.

### [LinearItemDecoration](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/LinearItemDecoration)
- **Purpose**: RecyclerView linear item spacing with optional colored dividers and padding.

---

## 7. Coroutine Management

### [CoroutinesHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/CoroutinesHolder)
- **Purpose**: Global coroutine scopes (`Default`, `Main`, `IO`) for simplified launching.

---

## 8. Multi-language Management

### [String Resource Manager](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/StringResource)
- **Purpose**: Modular, template-based string resource management for localization.
- **Features**: Centralized registration and retrieval, suitable for multi-module or multi-feature projects.

---

> This document was created with the assistance of ChatGPT.