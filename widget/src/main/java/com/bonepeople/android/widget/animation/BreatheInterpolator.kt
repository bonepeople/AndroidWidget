package com.bonepeople.android.widget.animation

import android.animation.TimeInterpolator

/**
 * 呼吸节奏的插值器
 *
 * 输入[0,1]区间的浮点数字，返回[0,1]区间的浮点数字。
 */
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
        return if (interpolatorMode == FROM_BOTTOM) { // 默认情况，从底部开始变化
            if (input < middle) {
                ((kotlin.math.cos((input / middle + 1) * kotlin.math.PI) + 1) / 2).toFloat()
            } else {
                ((kotlin.math.cos((input - middle) / (1 - middle) * kotlin.math.PI) + 1) / 2).toFloat()
            }
        } else { // 从顶部开始变化，方便循环动画开始时以顶点为起点
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