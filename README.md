# AndroidWidget

本项目提供的工具主要用于开发安卓APP。

## 简介

在平时的开发过程中，我们经常会用到各种各样的工具类，这些工具类随着时间的推移渐渐落后于时代，有些工具类由于质量不佳、疏于维护对开发造成了影响，甚至会造成一些严重的问题。

基于以上问题，我开始着手总结一套常用的工具集，既解决了老旧代码的问题，又能提高开发效率。

此项目是以我的工作经验为主，再结合网络中一些优秀的开源代码开发而来。项目中的代码都是经过多方面的考察才确定的，也是对网络上一些工具的提炼。

此项目会进行长期的维护，为了方便使用，也会尽可能的添加注释。

## 使用方法

### 接入

+ 编辑`settings.gradle`，添加`maven { url 'https://jitpack.io' }`

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
        //...
    }
}
```

+ 在模块的`build.gradle`中添加依赖

```groovy
dependencies {
    //...

    // AndroidWidget https://github.com/bonepeople/AndroidWidget
    implementation 'com.github.bonepeople:AndroidWidget:1.5.3'
}
```

### 初始化
+ 该项目使用了`AppStartup`技术，会在APP打开的时候自动初始化，默认情况下不需要用户进行任何处理。
+ 由于`AppStartup`技术不支持多进程自动初始化的功能，在客户端设计多进程的时候需要手动进行初始化。
+ 我对手动初始化的逻辑进行了处理，用户仅需在`Application.onCreate`的方法中调用初始化函数即可。
```kotlin
import android.app.Application
import androidx.startup.StartupHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StartupHelper.initializeAll(this)
        //...
    }
}
```
> 为确保基础组件在使用前完成初始化操作，请确保此函数提前调用。  
> 依赖库中大部分组件使用懒加载技术，仅在首次使用的时候进行实例化，初始化操作不会影响启动速度。  
> 重复调用此方法不会导致多次初始化。

## 常用工具的使用方式

### [AppGson][AppGson.kt] - Gson数据转换工具类

+ toJson
  ```kotlin
  val json = AppGson.toJson(Data())
  ```
  > 此方法与Gson().toJson()的效果一致,默认使用全局的Gson单例进行序列化，也可以传入指定的Gson实例进行序列化。
+ toObject
  ```kotlin
  val data: Data = AppGson.toObject("{}")
  ```
  > 此方法利用kotlin的类型内联进行反序列化，在使用此方法的时候不再需要指定对象类型。
+ addNotNullAdapter
  ```kotlin
  //为默认的gson实例添加去除空值的适配器
  val gson = AppGson.addNotNullAdapter(AppGson.defaultGson)
  //使用附带去除空值适配器的gson解析json字符串
  val data: Data = AppGson.toObject("{}", gson)
  ```
  > 如果解析的json字符串中包含`null`，解析后会使对象的字段变为`null`，这样会影响我们的正常使用，同时kotlin的空安全会抛出异常。
  > 为了解决这个问题，我们可以添加一个可以去除空值的适配器，在解析的过程中将`null`去除。
  > 这样我们就不需要在每次使用变量的时候进行空值判断了。

### [AppPermission][AppPermission.kt] - 权限申请工具类

+ 检查权限
  ```kotlin
  AppPermission.check(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    .onResult { allGranted, permissionResult ->
        if (allGranted) {
          //所有权限均已获取
        } else {
          //有部分权限未授予，可以通过遍历permissionResult进行判断并提示
        }
  }
  ```
  > 对于仅需要检查权限授予状态的情况，可以使用此方法，如果需要申请权限，请使用`AppPermission.request`方法。
+ 申请权限
  ```kotlin
  //简单用法
  AppPermission.request(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
      .onGranted {
          //所有权限均已获取
      }
  //常规用法
  AppPermission.request(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
      .onResult { allGranted, permissionResult ->
          if (allGranted) {
              //所有权限均已获取
          } else {
              //有部分权限未授予，可以通过遍历permissionResult进行判断并提示
          }
      }
  ```
  > 简单用法适用于不需要处理权限拒绝情况的业务逻辑，该回调仅在用户全部授权的情况下触发。  
  > 可以同时申请多个权限，在申请之前会对未授予的权限进行筛选。  
  > 与其他权限申请框架不同，使用AppPermission.request()方法将实时发起权限申请，不强制要求设置回调方法以及调用申请操作，使用更简单。

[AppGson.kt]: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt

[AppPermission.kt]: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt