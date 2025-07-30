Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AndroidWidget

A practical Android utility library for everyday development — covering JSON parsing, permission requests, Activity navigation, View extensions, system bar adaptation, and more. Drop it in and start using it with minimal boilerplate.

## At a Glance

- **Purpose**: A curated, battle-tested set of Android utilities that replaces outdated, hard-to-maintain helper classes and improves development efficiency and code quality.
- **Maintenance**: Actively maintained with well-reviewed code and helpful comments for easy reading and extension.
- **Initialization**: Auto-initialized via Jetpack App Startup — no extra setup required by default. For multi-process apps, call `StartupHelper.initializeAll(this)` in `Application.onCreate`.

## Documentation

For a full project overview, highlighted features, and integration guide, see **[document/README.md](document/README.md)**.

To browse all feature modules and detailed usage, see **[document/features/README.md](document/features/README.md)** ([中文](document/features/README.zh-CN.md) | [Español](document/features/README.es-ES.md)).

## Integration

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

// build.gradle
dependencies {
    implementation 'com.github.bonepeople:AndroidWidget:1.7.4'
}
```

## Repository

https://github.com/bonepeople/AndroidWidget