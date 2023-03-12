package com.bonepeople.android.widget.simple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bonepeople.android.widget.simple.databinding.ActivityResultBinding
import com.bonepeople.android.widget.util.AppView.singleClick

class ResultActivity : AppCompatActivity() {
    private val views: ActivityResultBinding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        title = "ActivityResult"
        initView()
        initData(savedInstanceState)
    }

    private fun initView() {
        views.button1.singleClick {
            val intent = Intent().apply {
                putExtra("result", 1)
            }
            setResult(RESULT_OK, intent)
            finishAfterTransition()
        }
        views.button2.singleClick {
            setResult(RESULT_CANCELED)
            finishAfterTransition()
        }
    }

    private fun initData(savedInstanceState: Bundle?) {

    }
}