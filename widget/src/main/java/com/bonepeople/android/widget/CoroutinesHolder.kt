package com.bonepeople.android.widget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Coroutines状态保存器
 */
@Suppress("UNUSED")
object CoroutinesHolder {
    /**
     * 默认线程的全局协程单例
     * + 适用于CPU密集型任务
     * + 使用共享的后台线程池，线程数为CPU核心数
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

    /**
     * IO线程的全局协程单例
     * + 适用于IO密集型任务
     * + 基于线程的协程调度器，会根据需要自动调整线程池的大小
     */
    val io: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.IO + Job()
    }
}