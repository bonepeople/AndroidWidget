Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# ActivityHolder

**Link to source code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ActivityHolder.kt

`ActivityHolder` is a utility object that helps manage the lifecycle, state, and memory-resident data of all active `Activity` instances in an Android application.

> 📄 This documentation was assisted by ChatGPT.

## Usage

### Top Activity Access

You can always retrieve the currently visible (top) activity:

```kotlin
val currentActivity = ActivityHolder.getTopActivity()
```

### Managing Temporary Activity Data

You can store temporary key-value data associated with a specific activity instance.

#### Store data:

```kotlin
ActivityHolder.putData(activity, "key", value)
```

#### Retrieve data:

```kotlin
val value = ActivityHolder.getData(activity, "key")
```

#### Remove data:

```kotlin
ActivityHolder.removeData(activity, "key")
```

### Get All Active Activities

```kotlin
val allActivities = ActivityHolder.activityList
```

Maintains the order in which the activities were opened.

## Notes

- Useful for global access, cross-activity communication, or cleanup logic.