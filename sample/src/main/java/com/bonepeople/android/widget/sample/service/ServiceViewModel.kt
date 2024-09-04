package com.bonepeople.android.widget.sample.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonepeople.android.widget.sample.LogUtil
import com.bonepeople.android.widget.util.AppSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel() {
    val serviceRunning = MutableStateFlow(false)

    init {
        updateServiceStatus()
    }

    fun updateServiceStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            LogUtil.test.debug("checkServiceRunning")
            serviceRunning.value = AppSystem.checkServiceRunning(MyService::class.java)
        }
    }
}