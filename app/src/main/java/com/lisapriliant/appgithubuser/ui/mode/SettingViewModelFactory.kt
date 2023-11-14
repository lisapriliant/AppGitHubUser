package com.lisapriliant.appgithubuser.ui.mode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModeSettingViewModel::class.java)) {
            return ModeSettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}