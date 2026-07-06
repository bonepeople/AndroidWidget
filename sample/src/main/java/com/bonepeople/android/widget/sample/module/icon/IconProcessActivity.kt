package com.bonepeople.android.widget.sample.module.icon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bonepeople.android.widget.sample.databinding.ActivityIconProcessBinding

class IconProcessActivity : AppCompatActivity() {
    private val views: ActivityIconProcessBinding by lazy { ActivityIconProcessBinding.inflate(layoutInflater) }
    private val safeZoneOverlays by lazy {
        listOf(
            views.imageSafeZoneOverlayStep05,
            views.imageSafeZoneOverlayStep06,
            views.imageSafeZoneOverlayStep07,
            views.imageSafeZoneOverlayStep08,
            views.imageSafeZoneOverlayStep09
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        initView()
    }

    private fun initView() {
        views.switchSafeZone.setOnCheckedChangeListener { _, isChecked ->
            safeZoneOverlays.forEach { it.isVisible = isChecked }
        }
    }
}