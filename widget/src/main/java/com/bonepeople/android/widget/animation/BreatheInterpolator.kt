package com.bonepeople.android.widget.animation

import android.animation.TimeInterpolator

@Suppress("UNUSED")
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
    private val middle = 1 - 0.618
    private var interpolatorMode = FROM_BOTTOM

    constructor(mode: Int) : this() {
        if (mode == FROM_BOTTOM || mode == FROM_TOP) {
            this.interpolatorMode = mode
        }
    }

    override fun getInterpolation(input: Float): Float {
        return if (interpolatorMode == FROM_BOTTOM) {
            if (input < middle) {
                ((kotlin.math.cos((input / middle + 1) * kotlin.math.PI) + 1) / 2).toFloat()
            } else {
                ((kotlin.math.cos((input - middle) / (1 - middle) * kotlin.math.PI) + 1) / 2).toFloat()
            }
        } else {
            if (input < (1 - middle)) {
                ((kotlin.math.cos(input / (1 - middle) * kotlin.math.PI) + 1) / 2).toFloat()
            } else {
                ((kotlin.math.cos(((input - (1 - middle)) / middle + 1) * kotlin.math.PI) + 1) / 2).toFloat()
            }
        }
    }

    companion object {
        const val FROM_BOTTOM = 1
        const val FROM_TOP = 2
    }
}