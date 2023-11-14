package com.lisapriliant.appgithubuser.ui.mode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ModeSettingViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getModeSetting(): LiveData<Boolean> {
        return pref.getModeSetting().asLiveData()
    }

    fun saveModeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveModeSetting(isDarkModeActive)
        }
    }
}