多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AndroidWidget

一套面向 Android 日常开发的实用工具库，覆盖 JSON 解析、权限申请、Activity 跳转、View 扩展、系统栏适配等常见场景，开箱即用，减少重复造轮子。

## 快速了解

- **项目定位**：整理并维护一套经过实践检验的 Android 工具集，替代老旧、难维护的零散工具类，提升开发效率与代码质量。
- **维护方式**：长期维护，代码经过多轮考察与提炼，并尽可能补充注释，便于阅读与扩展。
- **初始化**：基于 Jetpack App Startup 自动初始化，默认无需额外配置；多进程场景在 `Application.onCreate` 中调用 `StartupHelper.initializeAll(this)` 即可。

## 文档

更完整的项目介绍、亮点功能展示与接入说明，请查看 **[document/README.zh-CN.md](document/README.zh-CN.md)**。

如需浏览全部功能模块及详细用法，请继续查看 **[document/features/README.zh-CN.md](document/features/README.zh-CN.md)**（[English](document/features/README.md) | [Español](document/features/README.es-ES.md)）。

## 接入

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

## 仓库

https://github.com/bonepeople/AndroidWidget