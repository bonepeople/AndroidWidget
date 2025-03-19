package com.bonepeople.android.widget.sample.global

import com.bonepeople.android.widget.util.AppLog

object LogUtil {
    val app = AppLog("AppLog.App")
    val test = AppLog("AppLog.Test").apply {
        showThreadInfo = true
        showStackInfo = true
    }
}