Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppSystem

## Introducción

`AppSystem` proporciona acceso a información y funciones a nivel de sistema en dispositivos Android, incluido estado de batería, direcciones IP, dimensiones de pantalla e información de procesos.

## Casos de uso

- Diagnóstico y monitorización de dispositivos
- Operaciones de red y ajustes de diseño UI

## Características

- Porcentaje de batería y listener de cambios
- ID de dispositivo y nombre de proceso
- Obtención de direcciones IPv4/IPv6 y dirección de broadcast
- Dimensiones de barra de estado, barra de navegación y pantalla
- Comprobación de servicios en ejecución

## Uso

Porcentaje de batería (0–100):

```kotlin
val percent = AppSystem.batteryPercent
```

Escuchar cambios de batería:

```kotlin
val receiver = AppSystem.registerBatteryChanged(context) { percent ->
    // Gestionar actualización de nivel
}
// Recuerda desregistrar el receiver al terminar
context.unregisterReceiver(receiver)
```

Información de dispositivo y proceso:

```kotlin
val androidId = AppSystem.androidId
val processName = AppSystem.processName
```

Direcciones IP (requiere permiso `INTERNET`):

```kotlin
val ipv4List = AppSystem.getIpAddressV4()
val ipv6List = AppSystem.getIpAddressV6()
val broadcast = AppSystem.getBroadcastAddress()
```

Dimensiones de pantalla y UI del sistema:

```kotlin
val statusBarHeight = AppSystem.getStatusBarHeight()
val navigationBarHeight = AppSystem.getNavigationBarHeight()
val screenWidth = AppSystem.getScreenWidth()
val screenHeight = AppSystem.getScreenHeight()
```

Comprobar servicios en ejecución:

```kotlin
val isRunning = AppSystem.checkServiceRunning(MyService::class.java)
```

## Notas

- En Android 8.0+ (API 26+), la comprobación de servicios está restringida y puede no ser precisa.

## Código fuente

[AppSystem.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSystem.kt)