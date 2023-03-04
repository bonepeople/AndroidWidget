package com.bonepeople.android.widget.view

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.bonepeople.android.dimensionutil.DimensionUtil

/**
 * 简单的加载对话框
 */
class SimpleLoadingDialog(activity: Activity) : AlertDialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressBar = ProgressBar(context).apply {
            layoutParams = FrameLayout.LayoutParams(DimensionUtil.getPx(80f), DimensionUtil.getPx(80f), Gravity.CENTER)
        }
        setContentView(progressBar)
        setCancelable(false)

        window?.run {
            attributes.width = DimensionUtil.getPx(120f)
            attributes.height = DimensionUtil.getPx(120f)

            val drawable = GradientDrawable().apply {
                cornerRadius = 20f
                setColor(0x80000000.toInt())
            }
            setBackgroundDrawable(drawable)
            // 弹窗中有输入框的时候需要清除对应的标志位才能弹出软键盘，适用于直接继承AlertDialog的情况
            // clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        }
    }
}