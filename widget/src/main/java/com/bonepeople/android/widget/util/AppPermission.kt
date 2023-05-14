package com.bonepeople.android.widget.util

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.activity.result.launch
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * 权限申请工具类
 *
 * 采用ActivityResultContract方式申请权限
 */
@Suppress("UNUSED")
class AppPermission private constructor() {
    private val permissionResult = LinkedHashMap<String, Boolean>()
    private var resultAction: (Boolean, LinkedHashMap<String, Boolean>) -> Unit = { _, _ -> }
    private var grantedAction: () -> Unit = {}
    private var finished = false

    /**
     * 设置权限全部授予时的动作
     */
    fun onGranted(action: () -> Unit) = apply {
        if (finished) {
            if (permissionResult.values.all { it }) {
                action.invoke()
            }
        } else {
            grantedAction = action
        }
    }

    /**
     * 设置获取权限申请结果后的动作
     *
     * allGranted - 是否全部授予权限
     * permissionResult - 每一项权限及其所对应的申请结果
     */
    fun onResult(action: (allGranted: Boolean, permissionResult: LinkedHashMap<String, Boolean>) -> Unit) = apply {
        if (finished) {
            val granted = permissionResult.values.all { it }
            action.invoke(granted, permissionResult)
        } else {
            resultAction = action
        }
    }

    companion object {
        /**
         * 检查权限授予情况
         */
        fun check(vararg permissions: String): AppPermission {
            return AppPermission().apply {
                permissions.forEach {
                    permissionResult[it] = ContextCompat.checkSelfPermission(ApplicationHolder.app, it) == PackageManager.PERMISSION_GRANTED
                }
                finished = true
            }
        }

        /**
         * 发起权限申请
         */
        fun request(vararg permissions: String): AppPermission {
            return AppPermission().apply {
                val requestList = LinkedList<String>()
                permissions.forEach {
                    if (ContextCompat.checkSelfPermission(ApplicationHolder.app, it) == PackageManager.PERMISSION_GRANTED) {
                        permissionResult[it] = true
                    } else {
                        permissionResult[it] = false
                        requestList.add(it)
                    }
                }
                if (requestList.isEmpty()) { //所有权限均已授予
                    finished = true
                } else { //申请未授予的权限
                    RequestMultiplePermissions().createIntent(ApplicationHolder.app, requestList.toTypedArray()).launch()
                        .onResult { result ->
                            permissionResult.putAll(RequestMultiplePermissions().parseResult(result.resultCode, result.data))
                            val granted = permissionResult.values.all { it }
                            resultAction.invoke(granted, permissionResult)
                            if (granted) grantedAction.invoke()
                            finished = true
                        }
                }
            }
        }
    }
}