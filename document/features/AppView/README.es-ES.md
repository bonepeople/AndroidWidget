Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppView

## Introducción

`AppView` proporciona extensiones para componentes `View` de Android, incluida prevención de clics repetidos, control de visibilidad, almacenamiento de datos por vista y utilidades de medición de diseño.

## Características

- Prevención de clics rápidos repetidos con intervalo configurable
- Helpers de visibilidad (`show`, `hide`, `gone`, `switchShow`, `switchVisible`)
- Almacenamiento de datos extra por vista (clave-valor)
- Análisis de `MeasureSpec` para diseño de vistas personalizadas

## Uso

Prevenir clics rápidos repetidos:

```kotlin
button.singleClick {
    // Acción al hacer clic de forma segura
}

button.singleClick(1000L) {
    // Intervalo personalizado de 1 segundo
}
```

Helpers de visibilidad:

```kotlin
view.show()                  // View.VISIBLE
view.hide()                  // View.INVISIBLE
view.gone()                  // View.GONE
view.switchShow(true)        // VISIBLE si true, GONE si false
view.switchVisible(false)    // INVISIBLE si false
```

Almacenamiento de datos extra por vista:

```kotlin
view.putExtra("key", 123)
val value: Int = view.getExtra("key", 0)
view.removeExtra("key")
```

Análisis de `MeasureSpec` para vistas personalizadas:

```kotlin
val param = AppView.resolveMeasureParameter(this, widthMeasureSpec, heightMeasureSpec)
val maxWidth = param.maxWidth
val heightMode = param.heightModeName
```

Ayuda a escribir mejor la lógica `onMeasure()` en vistas personalizadas.

## Código fuente

[AppView.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppView.kt)