package com.bonepeople.android.widget

import android.app.Activity
import android.os.Bundle
import java.util.*
import kotlin.collections.HashMap

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object ActivityHolder : DefaultActivityLifecycleCallbacks {
    private val activityData = HashMap<Activity, HashMap<String, Any>>()

    val activityList = LinkedList<Activity>()

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

    fun putData(activity: Activity, key: String, value: Any) {
        val map = activityData.getOrPut(activity) { HashMap() }
        map[key] = value
    }

    fun getData(activity: Activity, key: String): Any? {
        return activityData[activity]?.get(key)
    }

    fun removeData(activity: Activity, key: String) {
        activityData[activity]?.remove(key)
    }
}