package com.bonepeople.android.widget.sample.module.start

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.sample.R
import com.bonepeople.android.widget.sample.databinding.ActivityStartBinding
import com.bonepeople.android.widget.sample.module.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : FragmentActivity() {
    private val views: ActivityStartBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setOnExitAnimationListener { splashScreenView -> kotlin.runCatching { splashScreenView.remove() } }
        super.onCreate(savedInstanceState)
        setEdgeToEdge()
        setContentView(views.root)
        views.textVersion.text = getString(R.string.start_version_format, ApplicationHolder.getVersionName())
        playBrandAnimation()
        lifecycleScope.launch {
            delay(2_000)
            startActivity(Intent(this@StartActivity, HomeActivity::class.java))
            finishAfterTransition()
        }
    }

    private fun setEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }

    private fun playBrandAnimation() {
        views.imageLogo.animate().alpha(1f).setDuration(600).withEndAction {
            views.imageLogo.postDelayed({
                views.textAppName.visibility = View.VISIBLE
                views.textTagline.visibility = View.VISIBLE
            }, 200)
        }
    }
}