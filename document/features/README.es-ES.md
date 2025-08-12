Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Lista de características

Catálogo de módulos utilitarios proporcionados por AndroidWidget. Cada módulo tiene su propio README con detalles de uso y enlaces al código fuente.

## 1. Gestión de aplicación y actividades

#### [ApplicationHolder](./ApplicationHolder)
Acceso global a la instancia de `Application`, estado de depuración, versión e información del paquete. Inicialización automática mediante Jetpack Startup.

#### [ActivityHolder](./ActivityHolder)
Administrar todas las instancias activas de `Activity`, obtener la actividad superior, almacenar datos temporales por actividad y acceder a la lista de actividades.

## 2. Manejo de resultados de actividad y permisos

#### [ActivityResult / IntentLauncher](./ActivityResult)
Simplificar el flujo de `startActivityForResult` con manejadores encadenados `.onSuccess`, `.onFailure`, `.onResult`. Gestión automática del ciclo de vida y soporte para lanzamientos concurrentes.

#### [AppPermission](./AppPermission)
Simplificar las solicitudes de permisos en tiempo de ejecución — manejar múltiples permisos en una llamada, solicitando solo los no concedidos. `onGranted` para concesión total, `onResult` para estado detallado.

## 3. Almacenamiento de datos, serialización y seguridad

#### [AppData](./AppData)
Almacenamiento clave-valor moderno basado en Jetpack DataStore. API para corrutinas y síncrona, observación con `Flow`, múltiples instancias con nombre.

#### [AppGson](./AppGson)
Utilidad Gson mejorada con filtrado automático de nulos y deserialización genérica. Soporte para instancia Gson personalizable.

#### [AppEncrypt](./AppEncrypt)
Cifrado/descifrado AES y RSA para cadenas y flujos. Generación y análisis de pares de claves RSA.

#### [AppMessageDigest](./AppMessageDigest)
Hash MD5 para cadenas y flujos con seguimiento de progreso opcional. Ideal para verificación de archivos y procesamiento de archivos grandes.

#### [AppRandom](./AppRandom)
Generar cadenas, números y listas mezcladas aleatorias. Enteros basados en rango y reproducibilidad con semillas.

## 4. Herramientas de UI e interacción de usuario

#### [AppDensity](./AppDensity)
Convertir entre unidades `px`, `dp` y `sp`. Soporte para `DisplayMetrics` personalizados.

#### [AppKeyboard](./AppKeyboard)
Mostrar/ocultar el teclado y cerrarlo automáticamente al tocar fuera.

#### [AppLog](./AppLog)
Registro mejorado con información de hilo, ubicación del llamador e instancias reutilizables.

#### [AppSpan](./AppSpan)
Simplificar la creación de `SpannableString` con API encadenada para color de texto, fondo, subrayado, áreas clicables, etc.

#### [AppSystem](./AppSystem)
Obtener información de batería, red, pantalla y procesos.

#### [AppText](./AppText)
Formateo de cadenas, conversión de tamaño de archivos, transformaciones de bytes/hexadecimales.

#### [AppTime](./AppTime)
Formatear marcas de tiempo y duraciones con patrones, zonas horarias e idiomas personalizados.

#### [AppToast](./AppToast)
Mostrar toasts de forma segura en hilos, cambiando automáticamente al hilo principal e ignorando mensajes en blanco.

#### [AppView](./AppView)
Manejo seguro de clics, control de visibilidad, almacenamiento de datos por vista, utilidades `MeasureSpec`.

## 5. Operaciones de archivos

#### [AppZip](./AppZip)
Comprimir y descomprimir archivos y directorios conservando la estructura. Soporte de codificación UTF-8.

## 6. Animación y decoración de vistas

#### [BreatheInterpolator](./BreatheInterpolator)
Interpolador de animación estilo respiración con modos de abajo hacia arriba o de arriba hacia abajo.

#### [GridItemDecoration](./GridItemDecoration)
Decorador de espaciado para elementos de cuadrícula en RecyclerView, soporta diseños verticales y horizontales.

#### [LinearItemDecoration](./LinearItemDecoration)
Decorador de espaciado para elementos lineales en RecyclerView, con divisores de color opcionales y relleno.

## 7. Corrutinas y eventos

#### [CoroutinesHolder](./CoroutinesHolder)
Ámbitos globales de corrutinas (`Default`, `Main`, `IO`) para simplificar lanzamientos.

#### [AppEvent](./AppEvent)
Bus de eventos global con conocimiento del ciclo de vida, basado en `Flow`. Entrega no pegajosa, eventos tipados o genéricos con `CommonEvent`, modos de publicación `post` / `postAsync`.

## 8. Gestión multilingüe

#### [String Resource Manager](./StringResource)
Gestión modular y basada en plantillas de recursos de cadenas para localización. Registro y recuperación centralizados para proyectos con múltiples módulos.