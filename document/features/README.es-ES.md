Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Lista de características

## 1. Gestión de aplicación y actividades

### [ApplicationHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ApplicationHolder)
- **Propósito**: Acceso global a la instancia de `Application`, estado de depuración, versión e información del paquete.
- **Características**: Inicialización automática mediante Jetpack Startup, sin configuración manual.

### [ActivityHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityHolder)
- **Propósito**: Administrar todas las instancias activas de `Activity`, obtener la actividad superior, almacenar datos temporales por actividad y acceder a la lista de actividades.
- **Casos de uso**: Acceso global, comunicación entre actividades, limpieza del ciclo de vida.

---

## 2. Manejo de resultados de actividad y permisos

### [ActivityResult / IntentLauncher](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityResult)
- **Propósito**: Simplificar el flujo de `startActivityForResult` con manejadores encadenados `.onSuccess`, `.onFailure`, `.onResult`.
- **Características**: Gestión automática del ciclo de vida, soporte para lanzamientos concurrentes.

### [AppPermission](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppPermission)
- **Propósito**: Simplificar las solicitudes de permisos en tiempo de ejecución manejando múltiples permisos en una sola llamada, solicitando solo los no concedidos.
- **Características**: Callback `onGranted` para concesión total, `onResult` para estado detallado de permisos.

---

## 3. Almacenamiento de datos, serialización y seguridad

### [AppData](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppData)
- **Propósito**: Almacenamiento clave-valor moderno basado en Jetpack DataStore.
- **Características**: API para corrutinas y síncrona, observación con `Flow`, múltiples instancias con nombre.

### [AppGson](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppGson)
- **Propósito**: Utilidad Gson mejorada con filtrado automático de nulos y deserialización genérica.
- **Características**: Instancia Gson personalizable, mejor seguridad nula en Kotlin.

### [AppEncrypt](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppEncrypt)
- **Propósito**: Utilidad de cifrado/descifrado AES y RSA para cadenas y flujos.
- **Características**: Generación y análisis de pares de claves RSA, algoritmos seguros por defecto.

### [AppMessageDigest](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppMessageDigest)
- **Propósito**: Hash MD5 para cadenas y flujos con seguimiento de progreso opcional.
- **Casos de uso**: Verificación de archivos, procesamiento de archivos grandes.

### [AppRandom](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppRandom)
- **Propósito**: Generar cadenas, números y listas mezcladas aleatorias.
- **Características**: Enteros basados en rango, aleatoriedad reproducible con semillas.

---

## 4. Herramientas de UI e interacción de usuario

### [AppDensity](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppDensity)
- **Propósito**: Convertir entre unidades `px`, `dp` y `sp`.
- **Características**: Soporte para `DisplayMetrics` personalizados.

### [AppKeyboard](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppKeyboard)
- **Propósito**: Mostrar/ocultar el teclado y cerrarlo automáticamente al tocar fuera.

### [AppLog](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppLog)
- **Propósito**: Registro mejorado con información de hilo, ubicación del llamador e instancias reutilizables.

### [AppSpan](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppSpan)
- **Propósito**: Simplificar la creación y aplicación de estilos `SpannableString`.
- **Características**: API encadenada para color de texto, fondo, subrayado, áreas clicables, etc.

### [AppSystem](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppSystem)
- **Propósito**: Obtener información de batería, red, pantalla y procesos.

### [AppText](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppText)
- **Propósito**: Formateo de cadenas, conversión de tamaño de archivos, transformaciones de bytes/hexadecimales.

### [AppTime](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppTime)
- **Propósito**: Formatear marcas de tiempo y duraciones con patrones, zonas horarias e idiomas personalizados.

### [AppToast](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppToast)
- **Propósito**: Mostrar toasts de forma segura en hilos, cambiando automáticamente al hilo principal e ignorando mensajes en blanco.

### [AppView](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppView)
- **Propósito**: Manejo seguro de clics, control de visibilidad, almacenamiento de datos por vista, utilidades `MeasureSpec`.

---

## 5. Operaciones de archivos

### [AppZip](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppZip)
- **Propósito**: Comprimir y descomprimir archivos y directorios conservando la estructura.
- **Características**: Soporte de codificación UTF-8.

---

## 6. Animación y decoración de vistas

### [BreatheInterpolator](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/BreatheInterpolator)
- **Propósito**: Interpolador de animación estilo respiración con modos de abajo hacia arriba o de arriba hacia abajo.

### [GridItemDecoration](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/GridItemDecoration)
- **Propósito**: Decorador de espaciado para elementos de cuadrícula en RecyclerView, soporta diseños verticales y horizontales.

### [LinearItemDecoration](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/LinearItemDecoration)
- **Propósito**: Decorador de espaciado para elementos lineales en RecyclerView, con divisores de color opcionales y relleno.

---

## 7. Gestión de corrutinas

### [CoroutinesHolder](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/CoroutinesHolder)
- **Propósito**: Ámbitos globales de corrutinas (`Default`, `Main`, `IO`) para simplificar lanzamientos.

---

## 8. Gestión multilingüe

### [String Resource Manager](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/StringResource)
- **Propósito**: Gestión modular y basada en plantillas de recursos de cadenas para localización.
- **Características**: Registro y recuperación centralizados, ideal para proyectos con múltiples módulos o funcionalidades.

---

> Este documento fue creado con la asistencia de ChatGPT.