package com.bonepeople.android.widget.sample.module.icon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bonepeople.android.widget.sample.databinding.ActivityIconProcessBinding

class IconProcessActivity : AppCompatActivity() {
    private val views: ActivityIconProcessBinding by lazy { ActivityIconProcessBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
    }
}