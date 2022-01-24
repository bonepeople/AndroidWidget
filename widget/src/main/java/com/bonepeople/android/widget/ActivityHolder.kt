package com.bonepeople.android.widget

import android.app.Activity
import android.os.Bundle
import java.util.*

object ActivityHolder : DefaultActivityLifecycleCallbacks {
    val activityList = LinkedList<Activity>()

    fun getTopActivity(): Activity? {
        return if (activityList.isEmpty()) null else activityList.last
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.addLast(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
    }
}