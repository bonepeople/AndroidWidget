Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AndroidWidget

Una biblioteca de utilidades práctica para el desarrollo diario en Android: análisis JSON, solicitud de permisos, navegación entre Activities, extensiones de View, adaptación de barras del sistema y más. Lista para usar con un mínimo de código repetitivo.

## Resumen

- **Propósito**: Un conjunto curado y probado en producción de utilidades Android que sustituye clases auxiliares obsoletas y difíciles de mantener, mejorando la eficiencia y la calidad del código.
- **Mantenimiento**: Mantenimiento activo con código revisado y comentarios útiles para facilitar la lectura y la extensión.
- **Inicialización**: Inicialización automática mediante Jetpack App Startup; no requiere configuración adicional por defecto. En apps multiproceso, llame a `StartupHelper.initializeAll(this)` en `Application.onCreate`.

## Documentación

Para una descripción completa del proyecto, funciones destacadas y guía de integración, consulte **[document/README.es-ES.md](document/README.es-ES.md)**.

Para ver todos los módulos de funciones y su uso detallado, consulte **[document/features/README.es-ES.md](document/features/README.es-ES.md)** ([English](document/features/README.md) | [中文](document/features/README.zh-CN.md)).

## Integración

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

## Repositorio

https://github.com/bonepeople/AndroidWidget