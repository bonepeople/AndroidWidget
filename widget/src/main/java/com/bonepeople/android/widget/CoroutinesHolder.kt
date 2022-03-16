package com.bonepeople.android.widget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Coroutines状态保存器
 */
object CoroutinesHolder {
    /**
     * 默认线程的全局协程单例
     */
    val default: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Default + Job()
    }

    /**
     * 主线程的全局协程单例
     */
    val main: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()
    }
}