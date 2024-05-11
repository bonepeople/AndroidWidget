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
    }

    private fun initView() {
        views.buttonSuccess.singleClick {
            val intent = Intent().apply {
                putExtra("result", 1)
            }
            setResult(RESULT_OK, intent)
            finishAfterTransition()
        }
        views.buttonFailure.singleClick {
            setResult(RESULT_CANCELED)
            finishAfterTransition()
        }
    }
}