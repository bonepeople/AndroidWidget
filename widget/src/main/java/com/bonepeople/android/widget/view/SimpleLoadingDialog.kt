package com.bonepeople.android.widget.view

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.bonepeople.android.widget.util.AppDensity

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
        }
    }
}