package com.bonepeople.android.widget

import android.app.Activity
import android.os.Bundle
import java.util.*
import kotlin.collections.HashMap

/**
 * Activity状态保存器
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
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
    @Deprecated("这个扩展方法会污染代码提示，后续将被移除", ReplaceWith("ActivityHolder.putData(this, key, value)", "com.bonepeople.android.widget.ActivityHolder"))
    fun Activity.putExtra(key: String, value: Any) = putData(this, key, value)

    /**
     * 获取该Activity中指定Key所对应的Value
     */
    @Deprecated("这个扩展方法会污染代码提示，后续将被移除", ReplaceWith("ActivityHolder.getData(this, key)", "com.bonepeople.android.widget.ActivityHolder"))
    fun Activity.getExtra(key: String): Any? = getData(this, key)

    /**
     * 获取该Activity中指定的Key及所对应的Value
     */
    @Deprecated("这个扩展方法会污染代码提示，后续将被移除", ReplaceWith("ActivityHolder.removeData(this, key)", "com.bonepeople.android.widget.ActivityHolder"))
    fun Activity.removeExtra(key: String) = removeData(this, key)

    /**
     * 将指定的Key-Value数据放到[Activity]中
     */
    fun putData(activity: Activity, key: String, value: Any) {
        val map = activityData.getOrPut(activity) { HashMap() }
        map[key] = value
    }

    /**
     * 获取[Activity]中指定Key所对应的Value
     */
    fun getData(activity: Activity, key: String): Any? {
        return activityData[activity]?.get(key)
    }

    /**
     * 移除[Activity]中储存的数据
     */
    fun removeData(activity: Activity, key: String) {
        activityData[activity]?.remove(key)
    }
}