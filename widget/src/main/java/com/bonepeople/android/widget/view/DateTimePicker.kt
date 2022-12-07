package com.bonepeople.android.widget.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.bonepeople.android.widget.databinding.DialogDateTimePickerBinding
import com.bonepeople.android.widget.util.AppTime
import com.bonepeople.android.widget.util.singleClick
import java.util.Calendar

class DateTimePicker(private val manager: FragmentManager) : DialogFragment() {
    private var closing = false
    private val views by lazy { DialogDateTimePickerBinding.inflate(layoutInflater) }
    private val calendar = Calendar.getInstance()
    private var listener: (calendar: Calendar) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = views.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        views.numberPickerYear.minValue = 1900
        views.numberPickerYear.maxValue = 2099
        views.numberPickerYear.value = calendar[Calendar.YEAR]
        views.numberPickerYear.setOnValueChangedListener { _, _, newVal ->
            if (views.numberPickerMonth.value == 2) {
                updateMaxDay(newVal, 2)
            }
        }
        views.numberPickerMonth.minValue = 1
        views.numberPickerMonth.maxValue = 12
        views.numberPickerMonth.value = calendar[Calendar.MONTH]
        views.numberPickerMonth.setOnValueChangedListener { _, _, newVal ->
            updateMaxDay(views.numberPickerYear.value, newVal)
        }
        views.numberPickerDay.minValue = 1
        updateMaxDay(views.numberPickerYear.value, views.numberPickerMonth.value)
        views.numberPickerDay.value = calendar[Calendar.DAY_OF_MONTH]

        views.numberPickerHour.minValue = 0
        views.numberPickerHour.maxValue = 23
        views.numberPickerHour.setFormatter { AppTime.formatTimeNumber(it.toLong(), 2) }
        views.numberPickerHour.value = calendar[Calendar.HOUR_OF_DAY]

        views.numberPickerMinute.minValue = 0
        views.numberPickerMinute.maxValue = 59
        views.numberPickerMinute.setFormatter { AppTime.formatTimeNumber(it.toLong(), 2) }
        views.numberPickerMinute.value = calendar[Calendar.MINUTE]

        views.numberPickerSecond.minValue = 0
        views.numberPickerSecond.maxValue = 59
        views.numberPickerSecond.setFormatter { AppTime.formatTimeNumber(it.toLong(), 2) }
        views.numberPickerSecond.value = calendar[Calendar.SECOND]

        views.textViewOk.singleClick { onTimeSet() }
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

    private fun onTimeSet() {
        calendar.set(views.numberPickerYear.value, views.numberPickerMonth.value - 1, views.numberPickerDay.value, views.numberPickerHour.value, views.numberPickerMinute.value, views.numberPickerSecond.value)
        calendar[Calendar.MILLISECOND] = 0
        listener.invoke(calendar)
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

    fun show(time: Long = System.currentTimeMillis(), action: (calendar: Calendar) -> Unit) {
        if (isAdded) return
        if (time > 0) calendar.timeInMillis = time
        listener = action
        closing = false
        show(manager, null)
    }

    override fun dismiss() {
        if (isResumed) {
            super.dismiss()
        } else {
            closing = true
        }
    }

    companion object {
        fun create(activity: FragmentActivity) = DateTimePicker(activity.supportFragmentManager)
        fun create(fragment: Fragment) = DateTimePicker(fragment.childFragmentManager)
        fun create(fragmentManager: FragmentManager) = DateTimePicker(fragmentManager)
    }
}