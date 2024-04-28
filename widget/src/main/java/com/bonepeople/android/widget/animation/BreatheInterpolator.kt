package com.bonepeople.android.widget.animation

import android.animation.TimeInterpolator

/**
 * Interpolator for breathing rhythm animations.
 *
 * Accepts a floating-point number in the range [0,1] as input and returns a floating-point number in the range [0,1].
 */
@Suppress("Unused")
class BreatheInterpolator() : TimeInterpolator {
    /**
     *
    1+                  * *
     *               *       *
     *             *           *
     *            *              *
     *           *                  *
     *          *                      *
     *         *                          *
     *       *                                 *
     *     *                                        *
     *  **                                               *   *
    0+ ----------------------------------------------------------->
     * 0                                                         1
     */

    // The midpoint for the interpolation, derived from the golden ratio
    private val middle = 1 - 0.618
    private var interpolatorMode = FROM_BOTTOM

    /**
     * Secondary constructor to set the interpolation mode.
     * Acecepts a mod value of either [FROM_BOTTOM] or [FROM_TOP].
     */
    constructor(mode: Int) : this() {
        if (mode == FROM_BOTTOM || mode == FROM_TOP) {
            this.interpolatorMode = mode
        }
    }

    /**
     * Calculates the interpolated value for the given input.
     *
     * @param input A value in the range [0,1].
     * @return A value in the range [0,1] representing the interpolated output.
     */
    override fun getInterpolation(input: Float): Float {
        return if (interpolatorMode == FROM_BOTTOM) { // Default mode: starts from the bottom
            if (input < middle) {
                ((kotlin.math.cos((input / middle + 1) * kotlin.math.PI) + 1) / 2).toFloat()
            } else {
                ((kotlin.math.cos((input - middle) / (1 - middle) * kotlin.math.PI) + 1) / 2).toFloat()
            }
        } else { // Alternate mode: starts from the top for animations beginning at the peak
            if (input < (1 - middle)) {
                ((kotlin.math.cos(input / (1 - middle) * kotlin.math.PI) + 1) / 2).toFloat()
            } else {
                ((kotlin.math.cos(((input - (1 - middle)) / middle + 1) * kotlin.math.PI) + 1) / 2).toFloat()
            }
        }
    }

    companion object {
        const val FROM_BOTTOM = 1 // Interpolation starts from the bottom
        const val FROM_TOP = 2 // Interpolation starts from the top
    }
}