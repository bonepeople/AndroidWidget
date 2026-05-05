package com.bonepeople.android.widget.sample.module.start

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.sample.R
import com.bonepeople.android.widget.sample.module.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        lifecycleScope.launch {
            delay(2_000)
            startActivity(Intent(this@StartActivity, MainActivity::class.java))
            finishAfterTransition()
        }
    }
}