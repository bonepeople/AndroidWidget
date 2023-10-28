package com.bonepeople.android.widget.view

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.bonepeople.android.widget.util.AppDensity

/**
 * 简单的加载对话框
 */
class SimpleLoadingDialog(activity: Activity) : AlertDialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressBar = ProgressBar(context).apply {
            layoutParams = FrameLayout.LayoutParams(AppDensity.getPx(80f), AppDensity.getPx(80f), Gravity.CENTER)
        }
        setContentView(progressBar)
        setCancelable(false)

        window?.run {
            attributes.width = AppDensity.getPx(120f)
            attributes.height = AppDensity.getPx(120f)

            val drawable = GradientDrawable().apply {
                cornerRadius = 20f
                setColor(0x80000000.toInt())
            }
            setBackgroundDrawable(drawable)
            // 弹窗中有输入框的时候需要清除对应的标志位才能弹出软键盘，适用于直接继承AlertDialog的情况
            // clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            // 使用以下方法可以清除背景变暗的效果，在清除的同时不会导致状态栏文字变为白色
            // clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }
}