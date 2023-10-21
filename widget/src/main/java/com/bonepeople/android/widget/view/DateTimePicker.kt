package com.bonepeople.android.widget.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.databinding.DialogDateTimePickerBinding
import com.bonepeople.android.widget.util.AppText
import com.bonepeople.android.widget.util.AppTime
import com.bonepeople.android.widget.util.AppView.gone
import com.bonepeople.android.widget.util.AppView.show
import com.bonepeople.android.widget.util.AppView.singleClick
import java.util.Calendar

/**
 * 日期时间选择对话框
 */
@Suppress("UNUSED")
class DateTimePicker : DialogFragment() {
    private var dialogFragmentManager: FragmentManager? = null
    private var dialogFragmentTag: String = ""
    private var closing = false
    private val views by lazy { DialogDateTimePickerBinding.inflate(layoutInflater) }
    private val calendar = Calendar.getInstance()
    private var action: (calendar: Calendar) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = views.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.run { setCanceledOnTouchOutside(false) }
        views.textViewDate.singleClick {
            views.linearLayoutTime.gone()
            views.linearLayoutDate.show()
            views.textViewDate.alpha = 1f
            views.textViewTime.alpha = 0.5f
            TransitionManager.beginDelayedTransition(views.constraintLayoutTitle)
            ConstraintSet().also {
                it.clone(views.constraintLayoutTitle)
                it.connect(views.viewIndicator.id, ConstraintSet.START, views.textViewDate.id, ConstraintSet.START)
                it.connect(views.viewIndicator.id, ConstraintSet.END, views.textViewDate.id, ConstraintSet.END)
                it.applyTo(views.constraintLayoutTitle)
            }
        }
        views.textViewTime.singleClick {
            views.linearLayoutDate.gone()
            views.linearLayoutTime.show()
            views.textViewDate.alpha = 0.5f
            views.textViewTime.alpha = 1f
            TransitionManager.beginDelayedTransition(views.constraintLayoutTitle)
            ConstraintSet().also {
                it.clone(views.constraintLayoutTitle)
                it.connect(views.viewIndicator.id, ConstraintSet.START, views.textViewTime.id, ConstraintSet.START)
                it.connect(views.viewIndicator.id, ConstraintSet.END, views.textViewTime.id, ConstraintSet.END)
                it.applyTo(views.constraintLayoutTitle)
            }
        }
        views.numberPickerYear.minValue = 1900
        views.numberPickerYear.maxValue = 2099
        views.numberPickerYear.value = calendar[Calendar.YEAR]
        views.numberPickerYear.setOnValueChangedListener { _, _, newVal ->
            if (views.numberPickerMonth.value == 2) {
                updateMaxDay(newVal, 2)
            }
            updateTitle()
        }
        views.numberPickerMonth.minValue = 1
        views.numberPickerMonth.maxValue = 12
        views.numberPickerMonth.value = calendar[Calendar.MONTH] + 1
        views.numberPickerMonth.setOnValueChangedListener { _, _, newVal ->
            updateMaxDay(views.numberPickerYear.value, newVal)
            updateTitle()
        }
        views.numberPickerDay.minValue = 1
        updateMaxDay(views.numberPickerYear.value, views.numberPickerMonth.value)
        views.numberPickerDay.value = calendar[Calendar.DAY_OF_MONTH]
        views.numberPickerDay.setOnValueChangedListener { _, _, _ ->
            updateTitle()
        }

        views.numberPickerHour.minValue = 0
        views.numberPickerHour.maxValue = 23
        views.numberPickerHour.setFormatter { AppText.completeStart(it, "00") }
        views.numberPickerHour.value = calendar[Calendar.HOUR_OF_DAY]
        views.numberPickerHour.setOnValueChangedListener { _, _, _ ->
            updateTitle()
        }

        views.numberPickerMinute.minValue = 0
        views.numberPickerMinute.maxValue = 59
        views.numberPickerMinute.setFormatter { AppText.completeStart(it, "00") }
        views.numberPickerMinute.value = calendar[Calendar.MINUTE]
        views.numberPickerMinute.setOnValueChangedListener { _, _, _ ->
            updateTitle()
        }

        views.numberPickerSecond.minValue = 0
        views.numberPickerSecond.maxValue = 59
        views.numberPickerSecond.setFormatter { AppText.completeStart(it, "00") }
        views.numberPickerSecond.value = calendar[Calendar.SECOND]
        views.numberPickerSecond.setOnValueChangedListener { _, _, _ ->
            updateTitle()
        }

        views.buttonCancel.singleClick { dismiss() }
        views.buttonOk.singleClick { submit() }
        updateTitle()
    }

    override fun onResume() {
        super.onResume()
        if (closing) {
            dismiss()
        }
    }

    override fun onDestroyView() {
        (views.root.parent as? ViewGroup)?.removeView(views.root)
        super.onDestroyView()
    }

    private fun updateTitle() {
        calendar.set(
            views.numberPickerYear.value,
            views.numberPickerMonth.value - 1,
            views.numberPickerDay.value,
            views.numberPickerHour.value,
            views.numberPickerMinute.value,
            views.numberPickerSecond.value
        )
        val title = AppTime.getDateTimeString(calendar.timeInMillis).split(' ')
        views.textViewDate.text = title[0]
        views.textViewTime.text = title[1]
    }

    private fun submit() {
        action.invoke(calendar)
        dismiss()
    }

    private fun updateMaxDay(year: Int, month: Int) {
        val dayRange = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> if (year % 4 == 0) 29 else 28
        }
        views.numberPickerDay.maxValue = dayRange
    }

    /**
     * 设置用于显示Dialog的FragmentManager及Tag
     */
    fun setFragmentManagerAndTag(manager: FragmentManager, tag: String) = apply {
        dialogFragmentManager = manager
        dialogFragmentTag = tag
    }

    /**
     * 显示Dialog
     * + 对于已经显示的Dialog，重复调用此函数不会进行任何操作
     * + 在显示之前需要调用[setFragmentManagerAndTag]方法设置FragmentManager
     */
    fun show(time: Long = System.currentTimeMillis(), action: (calendar: Calendar) -> Unit) {
        require(dialogFragmentManager != null) { "需要通过setFragmentManagerAndTag方法设置FragmentManager及Tag" }
        if (isAdded) return
        if (time > 0) calendar.timeInMillis = time
        calendar[Calendar.MILLISECOND] = 0
        this.action = action
        closing = false
        show(dialogFragmentManager!!, dialogFragmentTag)
    }

    override fun dismiss() {
        if (isResumed) {
            super.dismiss()
        } else {
            closing = true
        }
    }

    companion object {
        /**
         * 展示日期时间选择对话框
         * @param time 用于初始化对话框展示的时间
         * @param action 用户点击确定后的动作，返回的[Calendar]可以使用[Calendar.getTimeInMillis]方法获取时间戳
         */
        fun show(time: Long = System.currentTimeMillis(), action: (calendar: Calendar) -> Unit) {
            val activity = ActivityHolder.getTopActivity()
            if (activity is FragmentActivity) {
                DateTimePicker().setFragmentManagerAndTag(activity.supportFragmentManager, "DateTimePicker").show(time, action)
            }
        }

        fun showSystemDialog(time: Long = System.currentTimeMillis(), action: (calendar: Calendar) -> Unit) {
            val activity = ActivityHolder.getTopActivity() ?: return
            val calendar = Calendar.getInstance()
            if (time > 0) calendar.timeInMillis = time
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    calendar.set(year, month, dayOfMonth, hourOfDay, minute, 0)
                    calendar[Calendar.MILLISECOND] = 0
                    action.invoke(calendar)
                }
                TimePickerDialog(activity, listener, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true).show()
            }
            DatePickerDialog(activity, listener, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
        }
    }
}