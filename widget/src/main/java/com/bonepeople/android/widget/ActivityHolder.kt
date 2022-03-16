package com.bonepeople.android.widget

import android.app.Activity
import android.os.Bundle
import java.util.*
import kotlin.collections.HashMap

/**
 * Activity状态保存器
 */
object ActivityHolder : DefaultActivityLifecycleCallbacks {
    private val activityData = HashMap<Activity, HashMap<String, Any>>()

    /**
     * Activity列表
     * 包含所有已开启的Activity，顺序和打开顺序一致
     */
    val activityList = LinkedList<Activity>()

    /**
     * 获取顶层Activity实例，无Activity时返回null
     */
    fun getTopActivity(): Activity? {
        return if (activityList.isEmpty()) null else activityList.last
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.addLast(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
        activityData.remove(activity)
    }

    /**
     * 将指定的Key-Value数据放到当前该Activity中
     */
    fun Activity.putExtra(key: String, value: Any) {
        val map = activityData.getOrPut(this) { HashMap() }
        map[key] = value
    }

    /**
     * 获取该Activity中指定Key所对应的Value
     */
    fun Activity.getExtra(key: String): Any? {
        return activityData[this]?.get(key)
    }

    /**
     * 获取该Activity中指定的Key及所对应的Value
     */
    fun Activity.removeExtra(key: String) {
        activityData[this]?.remove(key)
    }
}