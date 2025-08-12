Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# ActivityHolder

## Introduction

`ActivityHolder` is a utility object for managing the lifecycle, state, and in-memory data of all active `Activity` instances in an Android application.

## Use Cases

- Global access to the current activity
- Cross-activity communication
- Lifecycle-aware cleanup logic

## Features

- Retrieve the top (currently visible) activity
- Store and retrieve temporary key-value data per activity instance
- Access the ordered list of all active activities

## Usage

### Top activity access

```kotlin
val currentActivity = ActivityHolder.getTopActivity()
```

### Temporary activity data

Store data:

```kotlin
ActivityHolder.putData(activity, "key", value)
```

Retrieve data:

```kotlin
val value = ActivityHolder.getData(activity, "key")
```

Remove data:

```kotlin
ActivityHolder.removeData(activity, "key")
```

### All active activities

```kotlin
val allActivities = ActivityHolder.activityList
```

Maintains the order in which activities were opened.

## Source Code

[ActivityHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ActivityHolder.kt)