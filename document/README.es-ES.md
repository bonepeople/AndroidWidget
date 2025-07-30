Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Documentación de AndroidWidget

AndroidWidget es una biblioteca de utilidades práctica para el desarrollo diario en Android. Encapsula tareas recurrentes — análisis JSON, solicitud de permisos, callbacks de resultados de Activity, interacciones con View, adaptación de barras del sistema — en APIs concisas para que escriba menos código repetitivo y se concentre en la lógica de negocio.

Desarrollada a partir de años de experiencia en Android y refinada con las mejores prácticas de la comunidad. Los componentes se inicializan automáticamente mediante Jetpack App Startup, por lo que **funcionan al integrarlos** sin configuración tediosa.

---

## Por qué AndroidWidget

| Problema | Solución de AndroidWidget |
|----------|---------------------------|
| No hay `Context` o `Activity` disponible en utils, services o callbacks de SDK | `ApplicationHolder` & `ActivityHolder` — inicialización automática, acceso desde cualquier lugar |
| Null-safety de Gson, boilerplate de TypeToken, permisos y callbacks de Activity complicados | `AppGson`, `AppPermission`, `Intent.launch()` — APIs concisas en una línea |
| `SharedPreferences` sin soporte de corrutinas ni actualizaciones reactivas | `AppData` — almacenamiento basado en DataStore con APIs sync/async y observación `Flow` |
| Soluciones tipo EventBus causan fugas o requieren desregistro manual | `AppEvent` — suscripciones ligadas al ciclo de vida, canceladas al destruir |
| Código repetitivo de clics, visibilidad, texto enriquecido, Toast e insets en cada pantalla | `AppView`, `AppSpan`, `AppToast`, `InsetsLayout` — extensiones y layouts listos para usar |
| Logs de depuración sin ubicación del llamador, difíciles de rastrear | `AppLog` — info de hilo y stack trace con un solo toggle |

---

## Funciones destacadas

Las funciones siguientes se seleccionan por su impacto diario: menos boilerplate, menos bugs, iteración más rápida. No es una lista exhaustiva; consulte [features/README.es-ES.md](features/README.es-ES.md) para ver todo lo disponible.

### ApplicationHolder & ActivityHolder — Contexto global, cero configuración

Utilidades, tareas en segundo plano y callbacks de SDK de terceros suelen necesitar un `Context` o la `Activity` actual, pero no hay de dónde obtenerlo. Estos holders se **inicializan automáticamente vía Jetpack Startup** — sin registro manual ni pasar `Context` por parámetros:

```kotlin
// Application e info de depuración — desde cualquier lugar
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val version = ApplicationHolder.getVersionName()

// Activity actual — diálogos, deep links, callbacks de permisos
val activity = ActivityHolder.getTopActivity()
```

`ActivityHolder` también rastrea todas las Activities activas y admite almacenamiento temporal de datos por Activity.

👉 Documentación: [ApplicationHolder](features/ApplicationHolder/README.es-ES.md) · [ActivityHolder](features/ActivityHolder/README.es-ES.md)

---

### AppGson — Conversión JSON sin TypeToken ni problemas con null

Las peticiones de red y el almacenamiento local dependen de la serialización JSON. Gson puro exige especificar tipos manualmente, y los valores `null` obligan a proteger cada campo en Kotlin.

`AppGson` lo simplifica a una sola línea:

```kotlin
val json = AppGson.toJson(user)
val user: User = AppGson.toObject(json)  // sin TypeToken
```

La instancia Gson predeterminada incluye un adaptador que filtra nulls durante la deserialización.

👉 Documentación: [AppGson](features/AppGson/README.es-ES.md)

---

### Intent.launch — Navegación y callbacks encadenados

¿Sigue escribiendo `registerForActivityResult`, manteniendo referencias a Launcher y ramificando en callbacks? `Intent.launch()` comprime todo el flujo en una cadena:

```kotlin
Intent(this, DetailActivity::class.java)
    .launch()
    .onSuccess { data -> handleResult(data) }
    .onFailure { result -> handleCancel(result) }
```

Sin registro manual, gestión automática del ciclo de vida y soporte para lanzamientos concurrentes.

👉 Documentación: [ActivityResult / IntentLauncher](features/ActivityResult/README.es-ES.md)

---

### AppPermission — Solicitud de permisos al instante

Muchas bibliotecas de permisos requieren registrar un callback primero y luego activar la solicitud por separado. `AppPermission` inicia el flujo **en el momento de llamar a `request()`**:

```kotlin
AppPermission.request(Manifest.permission.CAMERA)
    .onGranted { openCamera() }
    .onResult { allGranted, resultMap ->
        if (!allGranted) showDeniedHint(resultMap)
    }
```

Los permisos ya concedidos se filtran automáticamente — sin diálogos redundantes.

👉 Documentación: [AppPermission](features/AppPermission/README.es-ES.md)

---

### AppData — Almacenamiento clave-valor moderno, SharedPreferences mejorado

¿Cansado del API bloqueante de `SharedPreferences` y la falta de actualizaciones reactivas? `AppData` envuelve Jetpack DataStore con APIs sync y de corrutinas, más observación `Flow` incluida:

```kotlin
val data = AppData.default

data.putStringSync("token", token)
val token = data.getStringSync("token", "")

data.getStringFlow("token").collect { updateUI(it) }
```

Cree múltiples almacenes con nombre: `AppData.create("settings")`.

👉 Documentación: [AppData](features/AppData/README.es-ES.md)

---

### AppEvent — Bus de eventos seguro con el ciclo de vida, sin fugas

La entrega global de eventos suele implicar eventos sticky, desregistro manual o callbacks tras destruir la Activity. `AppEvent` se basa en `Flow` con enlace al ciclo de vida — **suscríbase una vez, olvídese de la limpieza**:

```kotlin
AppEvent.register(this) { event ->
    when (event) {
        is LoginSuccessEvent -> refreshProfile()
        is CommonEvent -> if (event.name == "refresh") reloadData()
    }
}

AppEvent.postAsync(LoginSuccessEvent(user))
```

No sticky por defecto, eventos tipados soportados, entrega garantizada en el hilo principal.

👉 Documentación: [AppEvent](features/AppEvent/README.es-ES.md)

---

### AppView — Extensiones de View para interacciones cotidianas

Clics con debounce, cambios de visibilidad, datos temporales en Views — operaciones en casi todas las pantallas:

```kotlin
button.singleClick { submitOrder() }
loadingView.show()
errorView.gone()
emptyView.switchShow(list.isEmpty())
view.putExtra("position", index)
```

También incluye ayudas para analizar `MeasureSpec` en Views personalizados.

👉 Documentación: [AppView](features/AppView/README.es-ES.md)

---

### AppSpan — Texto enriquecido sin boilerplate de Spannable

Construir texto estilizado con `SpannableString` implica manejar clases span, índices y flags. `AppSpan` ofrece una API encadenada que se lee como el resultado:

```kotlin
textView.text = AppSpan()
    .textColor("Solo ", Color.GRAY)
    .textColor("hoy", Color.RED)
    .textSize(" — 50% dto.", 12f)
```

Funciona donde se acepte `CharSequence` — `TextView`, `Toast`, `Snackbar`, etc.

👉 Documentación: [AppSpan](features/AppSpan/README.es-ES.md)

---

### AppToast — Toast seguro desde cualquier hilo

Llamar a `Toast.makeText()` fuera del hilo principal provoca un crash. `AppToast` cambia de hilo automáticamente e ignora mensajes en blanco:

```kotlin
CoroutinesHolder.io.launch {
    val file = downloadReport()
    AppToast.show("Guardado en $file")
}
```

Una línea, sin `runOnUiThread`, sin comprobaciones de null.

👉 Documentación: [AppToast](features/AppToast/README.es-ES.md)

---

### InsetsLayout — Insets de barras del sistema y teclado, un layout lo resuelve

Adaptar barras de estado, navegación y teclado virtual suele implicar listeners repetitivos de `WindowInsets` en cada Activity. `InsetsLayout` lo unifica en un `ConstraintLayout` — **especifique los bordes en XML, el padding se adapta automáticamente**:

```xml
<com.bonepeople.android.widget.view.InsetsLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:insets="top|bottom">

    <!-- vistas hijas -->
</com.bonepeople.android.widget.view.InsetsLayout>
```

Basado en `WindowInsetsCompat` (barras del sistema + IME), responde dinámicamente al teclado y se suma al padding existente.

---

### AppLog — Logs de depuración que dicen de dónde vienen

La salida estándar de `Log.d()` no muestra qué hilo o línea la activó — rastrear problemas en proyectos grandes se vuelve adivinanza. `AppLog` añade ubicación del llamador e info de hilo con un solo toggle:

```kotlin
val log = AppLog.tag("Network")
log.showStackInfo = true
log.debug("request finished")
// → [main] request finished @ ApiService.fetch:42
```

Instancias por tag, interruptor global, cinco niveles — una mejora directa sobre `Log` nativo.

👉 Documentación: [AppLog](features/AppLog/README.es-ES.md)

---

## Inicio rápido

### 1. Agregar dependencia

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

// build.gradle del módulo
dependencies {
    implementation 'com.github.bonepeople:AndroidWidget:1.7.4'
}
```

### 2. Inicialización (normalmente no requerida)

La biblioteca usa Jetpack App Startup para inicializarse al abrir la app. **No se necesita código adicional por defecto.**

En apps multiproceso, llame esto al inicio de `Application.onCreate`:

```kotlin
import androidx.startup.StartupHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StartupHelper.initializeAll(this)
    }
}
```

> Las llamadas repetidas no causan doble inicialización. La mayoría de componentes usan carga diferida y no afectan la velocidad de arranque.

---

## Explorar más funciones

Las funciones destacadas anteriores se seleccionan por su impacto diario. La biblioteca completa también incluye:

- Cifrado y hash (AppEncrypt, AppMessageDigest)
- Compresión de archivos (AppZip)
- Formato de tiempo y texto (AppTime, AppText, AppDensity)
- Decoradores de RecyclerView (LinearItemDecoration, GridItemDecoration)
- Ámbitos globales de corrutinas (CoroutinesHolder)
- Control de teclado (AppKeyboard)
- Consultas de info del sistema (AppSystem)
- Gestión multilingüe de recursos (String Resource Manager)
- Y más …

👉 **Ver todas las funciones**: [features/README.es-ES.md](features/README.es-ES.md) ([English](features/README.md) | [中文](features/README.zh-CN.md))

Cada módulo de funciones tiene su propia guía de uso y ejemplos de código.

---

## Proyecto de ejemplo

El módulo `sample` del repositorio demuestra usos típicos de lanzamiento de Intent, solicitud de permisos, conversión Gson y más:

```
sample/src/main/java/com/bonepeople/android/widget/sample/
```

---

## Enlaces

- Repositorio: https://github.com/bonepeople/AndroidWidget
- Versiones: últimos tags en [JitPack](https://jitpack.io/#bonepeople/AndroidWidget)