Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# BreatheInterpolator

## Introducción

`BreatheInterpolator` es un interpolador de animación personalizado que simula un ritmo de respiración natural. Acepta un valor de entrada entre `0` y `1` y devuelve un valor modificado en el mismo rango, produciendo efectos de animación suaves y orgánicos.

## Casos de uso

- Botones con efecto respiración e indicadores de carga
- Cualquier elemento UI que necesite un patrón de easing natural

## Características

- Curva de easing estilo respiración
- Dos modos de interpolación:
  - `FROM_BOTTOM`: comienza desde el mínimo y sube (predeterminado)
  - `FROM_TOP`: comienza desde el máximo y baja

## Uso

Aplicar a una animación (p. ej. `ObjectAnimator` o `ValueAnimator`):

```kotlin
val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
    duration = 2000
    interpolator = BreatheInterpolator() // Modo predeterminado: FROM_BOTTOM
}
animator.start()
```

Especificar el modo para comenzar desde arriba:

```kotlin
val animator = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f).apply {
    duration = 3000
    interpolator = BreatheInterpolator(BreatheInterpolator.FROM_TOP)
}
animator.start()
```

Constantes de modo:

```kotlin
BreatheInterpolator.FROM_BOTTOM // value = 1
BreatheInterpolator.FROM_TOP    // value = 2
```

## Código fuente

[BreatheInterpolator.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/animation/BreatheInterpolator.kt)