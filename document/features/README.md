# Features List

# ActivityHolder

`ActivityHolder` is a lightweight utility for managing all active `Activity` instances in an Android app.  
It provides easy access to the top activity, temporary data storage per activity, and a full list of active activities.

## Key Features

- **Top Activity Access** – Quickly retrieve the currently visible `Activity`.
- **Per-Activity Data Storage** – Store and manage temporary key-value data tied to each activity.
- **Activity List Management** – Access all active activities in their creation order.

Ideal for global access, cross-activity communication, and lifecycle-aware cleanup tasks.

# ActivityResult

**Simplify Android `startActivityForResult` workflow**

`IntentLauncher` is a lightweight utility that lets developers launch activities and handle results in a fluent, reactive way using the `.launch()` extension. It automatically manages lifecycle and registration, so no manual setup is required.

### Key Features
- **Fluent API:** Chain `.onSuccess`, `.onFailure`, and `.onResult` for cleaner result handling.
- **Automatic Lifecycle Management:** No need for manual registration.
- **Concurrent Launch Support:** Easily adjust `initialCapacity` for multiple simultaneous activity results.

Perfect for developers who want a concise, modern alternative to the traditional `startActivityForResult` approach.

# AppData

**A modern, coroutine-friendly key-value storage utility for Android**

`AppData` is a lightweight data storage class built on Jetpack DataStore. It provides both suspend and synchronous APIs, supports multiple named instances, and offers reactive data observation via `Flow`.

### Key Features
- **Dual Access Modes:** Asynchronous (suspend) and synchronous methods for flexibility.
- **Reactive Support:** Observe data changes using `Flow`.
- **Multiple Named Stores:** Easily separate configurations for different modules or users.
- **Modern SharedPreferences Alternative:** Coroutine-safe with built-in version and registry management.

Ideal for replacing `SharedPreferences` in apps that need structured, concurrent, and reactive data storage.

# AppDensity

**Simple pixel density conversion for Android**

`AppDensity` provides utility functions to easily convert between `px`, `dp`, and `sp` units. It supports both automatic `DisplayMetrics` detection and custom metrics for precise control.

### Key Features
- **Bidirectional Conversion:** Convert `dp`/`sp` to `px` and vice versa.
- **Custom DisplayMetrics Support:** Override system metrics when needed.
- **Lightweight & Convenient:** Ideal for handling screen density–related calculations in UI development.

# AppEncrypt

**AES & RSA encryption utility for Android**

`AppEncrypt` provides a simple interface for performing AES and RSA encryption/decryption on both strings and streams. It also supports RSA key pair generation and decoding, making it a flexible tool for securing sensitive data in Android apps.

### Key Features
- **AES Encryption/Decryption:** Handle strings or file streams with optional salt.
- **RSA Encryption/Decryption:** Encrypt/decrypt data and generate/parse key pairs.
- **Stream & String Support:** Flexible for file processing or in-memory operations.
- **Secure Defaults:** Uses `AES/CBC/PKCS5Padding` and `RSA/ECB/PKCS1Padding` by default.

Ideal for developers who need a lightweight yet secure way to manage local or network data encryption.

# AppGson

**Enhanced Gson utility with automatic null filtering**

`AppGson` simplifies JSON serialization and deserialization in Android projects using Gson. It automatically removes `null` values during parsing, improving Kotlin null-safety and reducing boilerplate checks.

### Key Features
- **Automatic Null Removal:** Avoid `NullPointerException` in Kotlin by stripping nulls from JSON.
- **Easy Serialization & Deserialization:** Convert objects to JSON and JSON to typed objects.
- **Customizable:** Use your own `Gson` instance or enhance it with `addNotNullAdapter`.
- **Supports Generics:** Deserialize complex objects with type inference.

Perfect for developers who want a safer, cleaner approach to JSON handling in Android apps.

# AppKeyboard

**Simplified software keyboard management for Android**

`AppKeyboard` provides easy-to-use methods to show, hide, and manage the soft keyboard. It also helps detect touch events to automatically dismiss the keyboard when the user taps outside input fields.

### Key Features
- **Show & Hide Keyboard:** Programmatically control the soft keyboard.
- **Touch Detection:** Determine if a tap should trigger keyboard dismissal.
- **Centralized Management:** Simplifies keyboard behavior handling in activities and fragments.

Perfect for apps that need seamless input field interactions without manual keyboard handling.

# ApplicationHolder
A lightweight utility that provides centralized access to your Android app’s `Application` instance and key app-level information.

## Description
`ApplicationHolder` automatically initializes via Jetpack Startup, allowing developers to easily retrieve context, check debug mode, and access app version or package details from anywhere in the app.

## Key Features
- **Global Application Access**: Quickly retrieve the `Application` instance and package info.
- **Debug & Version Info**: Easily check debug mode, app version name, and version code.
- **Automatic Initialization**: No manual setup required thanks to Jetpack Startup.

# AppLog
A lightweight and flexible logging utility for Android that enhances the native `Log` class with thread info, stack traces, and per-tag reusable instances.

## Description
`AppLog` simplifies logging across your app by providing multiple log levels, global and per-instance control, and optional thread and stack trace details. It helps developers quickly pinpoint issues without cluttering the codebase.

## Key Features
- **Multiple Log Levels**: `verbose`, `debug`, `info`, `warn`, `error`.
- **Flexible Control**: Enable or disable logs globally or per instance.
- **Enhanced Debugging**: Optional thread name and class/line number in logs.
- **Reusable Loggers**: Create loggers with custom tags for different modules.

# AppMessageDigest
A simple utility for generating MD5 hashes from strings and input streams, ideal for file verification and content fingerprinting with optional progress tracking.

## Description
`AppMessageDigest` provides convenient methods to compute MD5 hashes for strings or large files, supporting chunked reading with customizable buffer sizes and progress callbacks for stream hashing.

## Key Features
- **MD5 Hashing**: Compute hashes for strings and input streams.
- **Progress Tracking**: Optional callback for monitoring bytes processed.
- **Flexible Buffering**: Customizable read buffer for large file handling.
- **File Verification**: Useful for integrity checks and duplicate detection.

# AppPermission
A lightweight utility for managing Android runtime permissions with simplified request and result handling.

## Description
`AppPermission` streamlines permission management by allowing you to check and request multiple permissions at once, automatically handling only ungranted permissions. It provides simple callbacks for both successful grants and detailed result handling without requiring complex setup.

## Key Features
- **Simplified Permission Requests**: Request multiple permissions in one call.
- **Automatic Filtering**: Only ungranted permissions are requested.
- **Flexible Callbacks**:
    - `onGranted` triggers when all requested permissions are granted.
    - `onResult` provides a full result map for detailed handling.
- **Immediate Execution**: Requests are triggered instantly without extra steps.

# AppRandom
A handy utility for generating random strings, numbers, and shuffled collections in Android applications.

## Description
`AppRandom` provides simple methods for creating random test data, unique identifiers, or randomized user experiences. It supports string generation with mixed characters, range-based integers, and collection shuffling without altering the original list.

## Key Features
- **Random String Generation**: Create alphanumeric strings of any length.
- **Random Integers in Range**: Quickly generate integers within a specified range.
- **List Shuffling**: Return a shuffled copy without modifying the original list.
- **Reproducible Randomness**: Uses a time-based seed while supporting predictable behavior if needed.

# AppSpan
A utility for building rich-text content with chained span styling in Android.

## Description
`AppSpan` extends `SpannableStringBuilder` to make creating styled text easy and readable. It provides fluent methods for applying text color, background color, size, and scaling, enabling developers to build dynamic and visually rich content for UI components like `TextView`, `Toast`, and `Snackbar`.

## Key Features
- **Fluent Text Styling**: Chain methods to apply multiple spans in sequence.
- **Multiple Style Options**: Foreground color, background color, absolute/relative size, and scaling.
- **Easy Integration**: Usable anywhere a `CharSequence` is supported.
- **Dynamic UI Content**: Ideal for highlights, labels, and composite styled text.

# AppSystem
A comprehensive Android utility for accessing system-level information such as battery status, network details, screen dimensions, and running services.

## Description
`AppSystem` simplifies device diagnostics and environment queries by providing easy access to battery levels, IP addresses, UI dimensions, and process info. It is useful for building features like network-aware apps, device monitoring, or adaptive UI layouts.

## Key Features
- **Battery Monitoring**: Retrieve battery percentage and listen for level changes.
- **Network Info**: Get IPv4/IPv6 addresses and broadcast addresses.
- **Device & Process Info**: Access Android ID, process name, and running services.
- **Screen & UI Dimensions**: Fetch status bar, navigation bar, and screen sizes.
- **Utility for Diagnostics**: Ideal for logging, monitoring, or adaptive UI scenarios.

# AppText
A versatile string utility for formatting, padding, file size conversion, and byte/hex transformations in Android applications.

## Description
`AppText` simplifies common text and data formatting tasks, including number formatting with separators, file size readability, string padding, line wrapping, and byte-to-hex conversions. It is ideal for creating user-friendly displays and handling low-level data conversions.

## Key Features
- **String Padding & Wrapping**: Easily complete strings or split long text into multiple lines.
- **Number & File Size Formatting**: Format numbers with thousands separators and convert byte sizes to readable units.
- **Byte/Hex Conversion**: Convert between byte arrays and hexadecimal strings.
- **UI-Friendly Output**: Enhance data readability for logs, displays, and file information.

# AppTime
A utility for formatting timestamps and durations into human-readable strings with support for custom patterns, time zones, and locales.

## Description
`AppTime` makes it easy to display formatted date-time strings and duration timers in Android. It supports millisecond precision, flexible formatting patterns, and localization, making it useful for logs, reports, and user-facing time displays.

## Key Features
- **Readable Date-Time Formatting**: Convert timestamps to human-friendly strings with optional milliseconds.
- **Custom Patterns & Time Zones**: Format time with your preferred pattern, locale, or zone.
- **Duration as Timer**: Display milliseconds as `HH:mm:ss.SSS` or simplified forms.
- **Legacy & Modern Support**: Works across pre-API 26 and newer Android versions.

# AppToast
A lightweight utility for displaying toast messages safely from anywhere in your Android app.

## Description
`AppToast` simplifies toast handling by automatically switching to the main thread using coroutines. It supports short and long messages while safely ignoring blank inputs, making it convenient for background or asynchronous operations.

## Key Features
- **Main Thread Safety**: Automatically runs on the main thread using coroutines.
- **Flexible Duration**: Supports both short and long toast messages.
- **Safe Handling**: Ignores null or blank messages to prevent unnecessary toasts.
- **Global Accessibility**: Trigger toasts from any part of the app.

# AppView
A utility providing helpful extensions for Android `View` components, including safe click handling, visibility control, extra data storage, and measurement utilities.

## Description
`AppView` enhances Android view operations with convenient functions to prevent rapid clicks, toggle visibility, store per-view data, and analyze layout measurements. It simplifies UI interactions and supports building custom views with cleaner code.

## Key Features
- **Single-Click Handling**: Prevent multiple clicks within a configurable interval.
- **Visibility Shortcuts**: Quickly show, hide, or toggle view states.
- **Per-View Data Storage**: Store and retrieve custom data directly on views.
- **MeasureSpec Utilities**: Assist with `onMeasure()` logic for custom view development.

# AppZip
A utility for compressing and decompressing files and directories in Android applications.

## Description
`AppZip` simplifies file compression tasks by supporting single-file zipping, multi-file or directory packaging, and full zip extraction. It preserves directory structures and uses UTF-8 encoding for compatibility with modern systems.

## Key Features
- **Single & Multi-File Compression**: Zip individual files or entire directories.
- **Directory Preservation**: Keeps folder structures intact during compression.
- **Zip Extraction**: Easily unzip files into a target directory.
- **UTF-8 Encoding Support**: Ensures compatibility with modern zip utilities.

# BreatheInterpolator
A custom animation interpolator that simulates a natural breathing rhythm for smooth and organic UI effects.

## Description
`BreatheInterpolator` modifies animation progress to create a subtle breathing-like motion. It supports two modes for rising or falling effects, making it ideal for animations like breathing buttons, pulsing indicators, or calming loading effects.

## Key Features
- **Natural Breathing Curve**: Creates smooth, rhythmic animation transitions.
- **Two Interpolation Modes**:
    - `FROM_BOTTOM` (default) – animation rises from minimum to maximum.
    - `FROM_TOP` – animation falls from maximum to minimum.
- **Easy Integration**: Works seamlessly with `ObjectAnimator` and `ValueAnimator`.

# CoroutinesHolder
A utility providing global singleton coroutine scopes for `Default`, `Main`, and `IO` dispatchers in Android.

## Description
`CoroutinesHolder` simplifies coroutine usage by offering ready-to-use `CoroutineScope` instances for CPU-bound, UI, and IO tasks. It helps avoid creating repetitive scopes, making it ideal for utility functions and library modules.

## Key Features
- **Predefined Scopes**: Access `Default`, `Main`, and `IO` coroutine scopes instantly.
- **Simplified Coroutine Launching**: Launch coroutines without manually creating new scopes.
- **Independent Jobs**: Each scope has its own cancelable `Job`.
- **Convenient for Utilities**: Useful when lifecycle-bound scopes are not required.

# GridItemDecoration
A RecyclerView utility for adding consistent spacing between grid items in Android.

## Description
`GridItemDecoration` simplifies the process of creating evenly spaced grids for both `GridLayoutManager` and `StaggeredGridLayoutManager`. It supports vertical and horizontal orientations, making it easy to achieve polished grid layouts without custom item views.

## Key Features
- **Customizable Spacing**: Set horizontal and vertical gaps in dp.
- **LayoutManager Support**: Works with both `GridLayoutManager` and `StaggeredGridLayoutManager`.
- **Orientation Friendly**: Compatible with vertical and horizontal grids.
- **Easy Integration**: Add as a simple `ItemDecoration` to any RecyclerView.

# LinearItemDecoration
A RecyclerView utility for adding customizable spacing and optional colored dividers in linear lists.

## Description
`LinearItemDecoration` enhances `LinearLayoutManager`-based RecyclerViews by providing flexible item spacing and optional colored dividers. It supports vertical and horizontal orientations, with configurable padding for refined UI appearance.

## Key Features
- **Customizable Item Spacing**: Define spacing between list items in dp.
- **Optional Colored Dividers**: Set a divider color to enhance visual separation.
- **Padding Support**: Apply start and end padding for dividers.
- **Orientation Support**: Works for both vertical and horizontal RecyclerViews.
- **Simple Integration**: Add as an `ItemDecoration` without modifying item layouts.

# String Resource Manager
A modular solution for managing localized string resources in Kotlin projects.

## Description
`StringResourceManager` and `StringTemplate` provide a structured way to define and retrieve localized strings, making it easy to maintain multilingual support across Android modules. Each string set is represented by a template class with language-specific implementations, ensuring clean and scalable resource management.

## Key Features
- **Template-Based Localization**: Define string templates as abstract classes with required keys.
- **Language-Specific Implementations**: Create locale-specific classes to supply translations.
- **Centralized Management**: Register and retrieve localized strings via `StringResourceManager`.
- **Modular & Scalable**: Ideal for multi-feature or multi-module projects requiring multilingual support.
