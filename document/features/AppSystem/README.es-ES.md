Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppSystem

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSystem.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppSystem` es una clase utilitaria que permite acceder a información y funciones del sistema en dispositivos Android, incluyendo batería, direcciones IP, dimensiones de pantalla y más.

> Útil para diagnóstico, operaciones de red, ajuste de interfaces y monitoreo del dispositivo.

## Cómo usarlo

### 1. Obtener información de la batería

#### Porcentaje de batería (0–100):

```kotlin
val porcentaje = AppSystem.batteryPercent
```

#### Escuchar cambios de batería:

```kotlin
val receptor = AppSystem.registerBatteryChanged(context) { porcentaje ->
    // Manejar el nuevo nivel de batería
}
// Recuerda cancelar el registro cuando ya no sea necesario
context.unregisterReceiver(receptor)
```

### 2. Información del dispositivo y proceso

```kotlin
val androidId = AppSystem.androidId
val nombreProceso = AppSystem.processName
```

### 3. Obtener direcciones IP (Requiere permiso INTERNET)

#### Direcciones IPv4:

```kotlin
val listaIPv4 = AppSystem.getIpAddressV4()
```

#### Direcciones IPv6:

```kotlin
val listaIPv6 = AppSystem.getIpAddressV6()
```

#### Dirección de difusión (broadcast):

```kotlin
val broadcast = AppSystem.getBroadcastAddress()
```

### 4. Dimensiones de pantalla y UI

```kotlin
val alturaBarraEstado = AppSystem.getStatusBarHeight()
val alturaBarraNavegacion = AppSystem.getNavigationBarHeight()
val anchoPantalla = AppSystem.getScreenWidth()
val altoPantalla = AppSystem.getScreenHeight()
```

### 5. Verificar si un servicio está en ejecución

```kotlin
val ejecutando = AppSystem.checkServiceRunning(MyService::class.java)
```

> ⚠ En Android 8.0+ (API 26+), la verificación de servicios está limitada y puede no ser precisa.