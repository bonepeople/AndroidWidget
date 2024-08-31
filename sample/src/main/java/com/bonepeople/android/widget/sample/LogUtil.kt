package com.bonepeople.android.widget.sample

import com.bonepeople.android.widget.util.AppLog

object LogUtil {
    val app = AppLog("AppLog.App")
    val test = AppLog("AppLog.Test").apply {
        showStackInfo = true
    }
}