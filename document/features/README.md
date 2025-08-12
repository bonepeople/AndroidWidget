Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# Features List

A catalog of utility modules provided by AndroidWidget. Each module has its own README with usage details and source code links.

## 1. Application & Activity Management

#### [ApplicationHolder](./ApplicationHolder)
Global access to the `Application` instance, debug status, version, and package information. Automatically initialized via Jetpack Startup.

#### [ActivityHolder](./ActivityHolder)
Manage all active `Activity` instances, retrieve the top activity, store temporary data per activity, and access the activity list.

## 2. Activity Result & Permission Handling

#### [ActivityResult / IntentLauncher](./ActivityResult)
Simplify the `startActivityForResult` workflow with chainable `.onSuccess`, `.onFailure`, `.onResult` handlers. Automatic lifecycle management and concurrent launch support.

#### [AppPermission](./AppPermission)
Simplify runtime permission requests — handle multiple permissions in one call, requesting only ungranted ones. `onGranted` for full grants, `onResult` for detailed permission states.

## 3. Data Storage, Serialization & Security

#### [AppData](./AppData)
Modern key-value storage based on Jetpack DataStore. Coroutine and synchronous APIs, `Flow` observation, multiple named instances.

#### [AppGson](./AppGson)
Enhanced Gson utility with automatic null filtering and generic deserialization. Customizable Gson instance support.

#### [AppEncrypt](./AppEncrypt)
AES and RSA encryption/decryption for strings and streams. RSA key pair generation and parsing.

#### [AppMessageDigest](./AppMessageDigest)
MD5 hashing for strings and streams with optional progress tracking. Suitable for file verification and large file processing.

#### [AppRandom](./AppRandom)
Generate random strings, numbers, and shuffled lists. Range-based integers and seed-based reproducibility.

## 4. UI Tools & User Interaction

#### [AppDensity](./AppDensity)
Convert between `px`, `dp`, and `sp` units. Supports custom `DisplayMetrics`.

#### [AppKeyboard](./AppKeyboard)
Show/hide the software keyboard and automatically dismiss on outside touch.

#### [AppLog](./AppLog)
Enhanced logging with thread info, caller location, and reusable log instances.

#### [AppSpan](./AppSpan)
Simplify `SpannableString` creation with a chainable API for text color, background, underline, clickable spans, and more.

#### [AppSystem](./AppSystem)
Retrieve battery, network, screen, and process information.

#### [AppText](./AppText)
String formatting, file size conversion, byte/hex transformations.

#### [AppTime](./AppTime)
Format timestamps and durations with custom patterns, time zones, and locales.

#### [AppToast](./AppToast)
Thread-safe toast display, automatically switches to the main thread, ignores blank messages.

#### [AppView](./AppView)
Safe click handling, visibility control, per-view data storage, `MeasureSpec` utilities.

## 5. File Operations

#### [AppZip](./AppZip)
Compress and decompress files and directories while preserving structure. UTF-8 encoding support.

## 6. Animation & View Decoration

#### [BreatheInterpolator](./BreatheInterpolator)
Breathing-style animation interpolator with bottom-to-top or top-to-bottom modes.

#### [GridItemDecoration](./GridItemDecoration)
RecyclerView grid item spacing for both vertical and horizontal layouts.

#### [LinearItemDecoration](./LinearItemDecoration)
RecyclerView linear item spacing with optional colored dividers and padding.

## 7. Coroutines & Events

#### [CoroutinesHolder](./CoroutinesHolder)
Global coroutine scopes (`Default`, `Main`, `IO`) for simplified launching.

#### [AppEvent](./AppEvent)
Lifecycle-aware global event bus based on `Flow`. Non-sticky delivery, typed or `CommonEvent` generic events, `post` / `postAsync` posting modes.

## 8. Multi-language Management

#### [String Resource Manager](./StringResource)
Modular, template-based string resource management for localization. Centralized registration and retrieval for multi-module projects.