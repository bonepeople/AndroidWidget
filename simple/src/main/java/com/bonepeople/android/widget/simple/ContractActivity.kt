package com.bonepeople.android.widget.simple

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bonepeople.android.widget.activity.result.launch
import com.bonepeople.android.widget.simple.databinding.ActivityContractBinding
import com.bonepeople.android.widget.util.AppGson
import com.bonepeople.android.widget.util.AppLog
import com.bonepeople.android.widget.util.AppPermission
import com.bonepeople.android.widget.util.singleClick

class ContractActivity : AppCompatActivity() {
    private val views: ActivityContractBinding by lazy { ActivityContractBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        title = "ActivityResultContract"
        initView()
        initData(savedInstanceState)
    }

    private fun initView() {
        views.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayOf(Normal, ActivityResult))
        views.button1.singleClick {
            when (views.spinner.selectedItem as String) {
                Normal -> {
                    val intent = Intent(this, ResultActivity::class.java)
                    startActivity(intent)
                }
                ActivityResult -> {
                    Intent(this, ResultActivity::class.java).launch()
                }
            }
        }
        views.button2.singleClick {
            when (views.spinner.selectedItem as String) {
                Normal -> {
                    val intent = Intent(this, ResultActivity::class.java)
                    startActivityForResult(intent, REQ_1)
                }
                ActivityResult -> {
                    Intent(this, ResultActivity::class.java).launch()
                        .onSuccess {
                            val result = it?.getIntExtra("result", 0)
                            AppLog.print("RESULT_OK - $result @ $ActivityResult")
                        }
                        .onFailure {
                            AppLog.print("RESULT_CANCEL @ $ActivityResult")
                        }
                }
            }
        }
        views.button3.singleClick {
            when (views.spinner.selectedItem as String) {
                Normal -> {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQ_2)
                }
                ActivityResult -> {
                    AppPermission.request(android.Manifest.permission.CAMERA)
                        .onGranted {
                            AppLog.print("permission result2 = granted @ $ActivityResult")
                        }
                        .onResult { _, permissionResult ->
                            AppLog.print("permission result2 = ${AppGson.toJson(permissionResult)} @ $ActivityResult")
                        }
                }
            }
        }
        views.button4.singleClick {
            when (views.spinner.selectedItem as String) {
                Normal -> {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQ_3)
                }
                ActivityResult -> {
                    AppPermission.request(android.Manifest.permission.CAMERA)
                        .onGranted {
                            AppLog.print("permission result3 = granted @ $ActivityResult")
                            Intent(MediaStore.ACTION_IMAGE_CAPTURE).launch()
                                .onSuccess {
                                    AppLog.print("TAKE_PICTURE_OK @ $ActivityResult")
                                }
                                .onFailure {
                                    AppLog.print("TAKE_PICTURE_CANCEL @ $ActivityResult")
                                }
                        }
                }
            }
        }
    }

    private fun initData(savedInstanceState: Bundle?) {

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_1 -> {
                if (resultCode == RESULT_OK) {
                    val result = data?.getIntExtra("result", 0)
                    AppLog.print("RESULT_OK - $result @ $Normal")
                } else {
                    AppLog.print("RESULT_CANCEL @ $Normal")
                }
            }
            REQ_4 -> {
                if (resultCode == RESULT_OK) {
                    AppLog.print("TAKE_PICTURE_OK @ $Normal")
                } else {
                    AppLog.print("TAKE_PICTURE_CANCEL @ $Normal")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_2 -> {
                AppLog.print("permission result2 = ${AppGson.toJson(grantResults)} @ $Normal")
            }
            REQ_3 -> {
                AppLog.print("permission result3 = ${AppGson.toJson(grantResults)} @ $Normal")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQ_4)
                }
            }
        }
    }

    companion object {
        const val Normal = "Normal"
        const val ActivityResult = "ActivityResult"
        const val REQ_1 = 1
        const val REQ_2 = 2
        const val REQ_3 = 3
        const val REQ_4 = 4
    }
}