package com.bonepeople.android.widget.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bonepeople.android.widget.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InsetsLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var initialPaddingLeft = paddingLeft
    private var initialPaddingTop = paddingTop
    private var initialPaddingRight = paddingRight
    private var initialPaddingBottom = paddingBottom
    var typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
        set(value) {
            field = value
            updatePadding()
        }
    var insetsMask: Int = INSET_NONE
        set(value) {
            field = value
            updatePadding()
        }
    private val currentInsets = MutableStateFlow(WindowInsetsCompat.Builder().build())
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.InsetsLayout, defStyleAttr, 0).let {
            insetsMask = it.getInt(R.styleable.InsetsLayout_insets, INSET_NONE)
            it.recycle()
        }
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            insets.also { currentInsets.value = it }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return
        coroutineScope.launch { currentInsets.collectLatest { updatePadding() } }
    }

    override fun onDetachedFromWindow() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDetachedFromWindow()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        initialPaddingLeft = left
        initialPaddingTop = top
        initialPaddingRight = right
        initialPaddingBottom = bottom
        updatePadding()
    }

    private fun needInsets(type: Int): Boolean {
        return (insetsMask and type) != 0
    }

    private fun updatePadding() {
        val systemInsets: Insets = if (isInEditMode) {
            Insets.of(0, (24 * resources.displayMetrics.density).toInt(), 0, (44 * resources.displayMetrics.density).toInt())
        } else {
            currentInsets.value.getInsets(typeMask)
        }
        val left: Int = initialPaddingLeft + if (needInsets(INSET_LEFT)) systemInsets.left else 0
        val top: Int = initialPaddingTop + if (needInsets(INSET_TOP)) systemInsets.top else 0
        val right: Int = initialPaddingRight + if (needInsets(INSET_RIGHT)) systemInsets.right else 0
        val bottom: Int = initialPaddingBottom + if (needInsets(INSET_BOTTOM)) systemInsets.bottom else 0
        super.setPadding(left, top, right, bottom)
    }

    companion object {
        const val INSET_NONE = 0
        const val INSET_TOP = 1
        const val INSET_BOTTOM = 2
        const val INSET_LEFT = 4
        const val INSET_RIGHT = 8
        const val INSET_ALL = INSET_TOP or INSET_BOTTOM or INSET_LEFT or INSET_RIGHT
    }
}