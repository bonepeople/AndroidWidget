Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Documentación de BreatheInterpolator

**Código fuente:** https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/animation/BreatheInterpolator.kt  
**Este documento fue creado con la asistencia de ChatGPT.*

## Descripción general

`BreatheInterpolator` es un interpolador personalizado diseñado para simular el ritmo natural de la respiración. Puede usarse para crear animaciones suaves y orgánicas en aplicaciones Android.

## Características

- Acepta un valor de entrada entre `0` y `1`, y devuelve un valor modificado en el mismo rango.
- Simula una curva de animación similar al patrón de respiración.
- Ofrece dos modos de interpolación:
    - `FROM_BOTTOM`: La animación comienza desde un valor mínimo y sube (modo predeterminado).
    - `FROM_TOP`: La animación comienza desde un valor máximo y baja.

## Cómo usarlo

### 1. Añadir el Interpolador

Para utilizar `BreatheInterpolator`, instáncialo y aplícalo a una animación (por ejemplo, `ObjectAnimator` o `ValueAnimator`):

```kotlin
val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
    duration = 2000
    interpolator = BreatheInterpolator() // Modo por defecto: FROM_BOTTOM
}
animator.start()
```

### 2. Especificar el modo (opcional)

Puedes especificar que la animación comience desde la parte superior en lugar de la inferior:

```kotlin
val animator = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f).apply {
    duration = 3000
    interpolator = BreatheInterpolator(BreatheInterpolator.FROM_TOP)
}
animator.start()
```

## Constantes

```kotlin
BreatheInterpolator.FROM_BOTTOM // valor = 1
BreatheInterpolator.FROM_TOP    // valor = 2
```

Utiliza estas constantes para controlar la dirección de la animación al crear el interpolador.

## Resumen

Este interpolador agrega un efecto de respiración sutil y rítmico a las animaciones, ideal para botones animados, indicadores de carga o cualquier elemento que requiera una transición natural.