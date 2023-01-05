package com.bonepeople.android.widget.util

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
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
        interface Intercepter {
            fun check(permission: String)
            suspend fun request(permission: String)
        }

        class NormalPermission : Intercepter {
            override fun check(permission: String) {
                if (ContextCompat.checkSelfPermission(ApplicationHolder.instance, permission) == PackageManager.PERMISSION_GRANTED) {
                    permissionResult[it] = true
                } else {
                    permissionResult[it] = false
                    requestList.add(it)
                }
            }

            override suspend fun request(permission: String) {

            }
        }

        /**
         * 同步的权限申请，需要在协程内发起
         */
        suspend fun requestEqueen(vararg permissions: String) {

        }

        /**
         * 发起权限申请
         */
        fun request(vararg permissions: String): AppPermission {
            return AppPermission().apply {
                val requestList = HashSet<String>()
                permissions.forEach {
                    NormalPermission().check(it)
                    if (it == android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            if (Environment.isExternalStorageManager()) {
                                permissionResult[it] = true
                            } else {
                                permissionResult[it] = false
                                requestList.add(it)
                            }
                        } else {
                            permissionResult[it] = false
                        }
                    } else {
                        if (ContextCompat.checkSelfPermission(ApplicationHolder.instance, it) == PackageManager.PERMISSION_GRANTED) {
                            permissionResult[it] = true
                        } else {
                            permissionResult[it] = false
                            requestList.add(it)
                        }
                    }
                }
                if (requestList.isEmpty()) { //所有权限均已授予
                    finished = true
                } else { //申请未授予的权限
                    val normalPermission = HashSet<String>()
                    requestList.forEach {
                        if (it == android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                    .setData(Uri.parse("package:${ApplicationHolder.getPackageName()}"))
                                    .launch()
                                    .onResult { _ ->
                                        permissionResult[it] = Environment.isExternalStorageManager()
                                    }
                            }
                        } else {
                            normalPermission.add(it)
                        }
                    }
                    RequestMultiplePermissions().createIntent(ApplicationHolder.instance, normalPermission.toTypedArray()).launch()
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