package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

/**
 * 日志输出工具类
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
class AppLog(val tag: String) {
    /**
     * 日志输出开关
     */
    var enable = true

    fun verbose(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.v(tag, message.toString(), throwable)
        }
    }

    fun debug(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.d(tag, message.toString(), throwable)
        }
    }

    fun info(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.i(tag, message.toString(), throwable)
        }
    }

    fun warn(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.w(tag, message.toString(), throwable)
        }
    }

    fun error(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.e(tag, message.toString(), throwable)
        }
    }

    companion object {
        /**
         * 全局的默认TAG
         */
        var defaultTag = "AppLog"

        @Deprecated("use defaultTag instead", ReplaceWith("AppLog.defaultTag"))
        var tag = defaultTag

        /**
         * 全局日志输出开关
         */
        var enable = ApplicationHolder.debug

        /**
         * AppLog实例集合
         */
        val instances = HashMap<String, AppLog>()

        /**
         * 获取指定标签的实例
         * + 未传入参数会返回默认标签的实例
         * + 创建的实例会保存在[instances]中，供下次使用
         */
        fun tag(tag: String = defaultTag): AppLog {
            return instances.getOrPut(tag) { AppLog(tag) }
        }

        fun verbose(message: Any?, throwable: Throwable? = null) = tag().verbose(message, throwable)
        fun debug(message: Any?, throwable: Throwable? = null) = tag().debug(message, throwable)
        fun info(message: Any?, throwable: Throwable? = null) = tag().info(message, throwable)
        fun warn(message: Any?, throwable: Throwable? = null) = tag().warn(message, throwable)
        fun error(message: Any?, throwable: Throwable? = null) = tag().error(message, throwable)
    }
}