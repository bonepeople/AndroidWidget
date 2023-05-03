package androidx.startup

import android.app.Application
import android.content.Context

/**
 * Startup初始化辅助工具类
 *
 * + Startup默认仅在主进程启动的时候进行自动初始化，对于有多个进程的程序可以通过此工具类手动触发初始化逻辑
 */
@Suppress("UNUSED")
object StartupHelper {
    /**
     * 初始化所有定义的[Initializer]
     * + 重复调用不会影响已经初始化的对象
     * + 推荐在[Application.onCreate]方法中调用，以确保初始化工作在使用之前完成
     */
    fun initializeAll(context: Context) {
        AppInitializer.getInstance(context).discoverAndInitialize()
    }
}