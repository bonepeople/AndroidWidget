Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppPermission

## Introducción

`AppPermission` proporciona comprobaciones síncronas de permisos en tiempo de ejecución y solicitudes basadas en corrutinas. Las solicitudes usan internamente el `ActivityResultContract` de AndroidX, comienzan en cuanto se ejecuta `request()` y devuelven el resultado directamente, sin cadenas de callbacks ni variables temporales.

## Características

- Comprobar uno o varios permisos de forma síncrona
- Consultar un permiso individual como `GRANTED` o `DENIED`
- Solicitar varios permisos desde una corrutina
- Distinguir entre un rechazo normal y uno permanente después de una solicitud
- Usa `ActivityResultContracts.RequestMultiplePermissions` de AndroidX para el flujo de permisos del sistema
- Inicia el flujo al llamar `request()`, sin registrar un callback ni realizar un lanzamiento por separado
- Solo solicita permisos aún no concedidos
- Devuelve si todos los permisos fueron concedidos y el estado de cada permiso

## Uso

Importar la nueva utilidad de permisos:

```kotlin
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.util.permission.AppPermission
import com.bonepeople.android.widget.util.permission.PermissionStatus
import kotlinx.coroutines.launch
```

Comprobar si todos los permisos están concedidos:

```kotlin
if (AppPermission.checkGranted(android.Manifest.permission.CAMERA)) {
    // El permiso de la cámara está concedido
}
```

Consultar el estado de un permiso:

```kotlin
when (AppPermission.checkStatus(android.Manifest.permission.CAMERA)) {
    PermissionStatus.GRANTED -> openCamera()
    PermissionStatus.DENIED -> showPermissionHint()
    PermissionStatus.PERMANENTLY_DENIED -> openAppSettings()
}
```

Solicitar permisos desde una corrutina consciente del ciclo de vida:

```kotlin
lifecycleScope.launch {
    val result = AppPermission.request(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (result.allGranted()) {
        startFeature()
    }

    val cameraStatus = result.permissionStatuses[android.Manifest.permission.CAMERA]
    if (cameraStatus == PermissionStatus.PERMANENTLY_DENIED) {
        openAppSettings()
    }
}
```

## Migración desde la API de callbacks

Reemplaza las cadenas de callbacks:

```kotlin
com.bonepeople.android.widget.util.AppPermission.request(permission)
    .onResult { allGranted, permissionResult ->
        if (allGranted) startFeature()
        val granted = permissionResult[permission] == true
    }
```

por la API de corrutinas:

```kotlin
lifecycleScope.launch {
    val result = AppPermission.request(permission)
    if (result.allGranted()) startFeature()
    val status = result.permissionStatuses[permission]
}
```

## Notas

- `checkGranted()` devuelve `true` solo cuando todos los permisos proporcionados están concedidos. Una lista vacía se considera concedida.
- `request()` es una función suspendida y debe llamarse desde una corrutina. Se recomienda un ámbito consciente del ciclo de vida, como `lifecycleScope`.
- `request()` omite los permisos ya concedidos, pero el resultado sigue incluyendo todos los permisos proporcionados en el orden original.
- `checkStatus()` solo puede determinar de forma síncrona si un permiso está concedido y devuelve `DENIED` en caso contrario. `PERMANENTLY_DENIED` solo se devuelve desde `request()`.
- Después de que se rechace un permiso, `request()` usa `ActivityCompat.shouldShowRequestPermissionRationale()` para determinar si el rechazo es permanente.
- La API antigua basada en callbacks sigue disponible en `com.bonepeople.android.widget.util.AppPermission`, pero el código nuevo debe usar la API documentada aquí.

## Código fuente

- [AppPermission.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/AppPermission.kt)
- [PermissionResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/PermissionResult.kt)
- [PermissionStatus.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/PermissionStatus.kt)
