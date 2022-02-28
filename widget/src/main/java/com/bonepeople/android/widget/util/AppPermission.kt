package com.bonepeople.android.widget.util

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import com.bonepeople.android.widget.activity.result.IntentLauncher
import com.bonepeople.android.widget.activity.result.createLauncher

/**
 * 权限申请工具类
 *
 * 采用ActivityResultContract方式申请权限
 */
class AppPermission private constructor() {
    private var launcher: IntentLauncher? = null
    private var resultAction: (Boolean, Map<String, Boolean>) -> Unit = { _, _ -> }
    private var grantedAction: () -> Unit = {}

    /**
     * 设置权限全部授予时的动作
     */
    fun onGranted(action: () -> Unit) = apply {
        grantedAction = action
    }

    /**
     * 设置获取权限申请结果后的动作
     *
     * allGranted - 是否全部授予权限
     * permissionResult - 每一项权限及其所对应的申请结果
     */
    fun onResult(action: (allGranted: Boolean, permissionResult: Map<String, Boolean>) -> Unit) = apply {
        resultAction = action
    }

    /**
     * 申请权限
     */
    fun launch() {
        launcher?.launch()
    }

    companion object {
        /**
         * 创建一个权限申请流程
         */
        fun request(permissions: Array<String>): AppPermission {
            val permissionRequest = AppPermission()
            val launcher = Intent(RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS).putExtra(RequestMultiplePermissions.EXTRA_PERMISSIONS, permissions).createLauncher()
                .onResult { result ->
                    val permissionResult = RequestMultiplePermissions().parseResult(result.resultCode, result.data)
                    val granted = permissionResult.values.all { it }
                    permissionRequest.resultAction.invoke(granted, permissionResult)
                    if (granted) permissionRequest.grantedAction.invoke()
                }
            permissionRequest.launcher = launcher
            return permissionRequest
        }
    }
}