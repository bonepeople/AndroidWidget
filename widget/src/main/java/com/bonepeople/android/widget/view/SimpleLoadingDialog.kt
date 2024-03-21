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
 * Simple Loading Dialog
 *
 * A basic loading dialog that displays a centered progress indicator.
 */
class SimpleLoadingDialog(activity: Activity) : AlertDialog(activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val progressBar = ProgressBar(context).apply {
            layoutParams = FrameLayout.LayoutParams(AppDensity.getPx(80f), AppDensity.getPx(80f), Gravity.CENTER)
        }
        setContentView(progressBar)
        setCancelable(false) // Prevents the dialog from being dismissed on touch outside or back press

        // Configure the dialog's window properties
        window?.run {
            attributes.width = AppDensity.getPx(120f)
            attributes.height = AppDensity.getPx(120f)

            // Set a rounded semi-transparent background for the dialog
            val drawable = GradientDrawable().apply {
                cornerRadius = 20f
                setColor(0x80000000.toInt())
            }
            setBackgroundDrawable(drawable)

            // Uncomment the following if dialog has an input field and the soft keyboard should be shown
            // clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            // Uncomment to disable dimming the background without affecting status bar text color
            // clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }
}