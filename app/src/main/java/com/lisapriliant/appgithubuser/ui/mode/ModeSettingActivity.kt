package com.lisapriliant.appgithubuser.ui.mode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.lisapriliant.appgithubuser.R
import com.lisapriliant.appgithubuser.databinding.ActivityModeSettingBinding

class ModeSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModeSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.mode_setting)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.datastore)
        val modeSettingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[
            ModeSettingViewModel::class.java
        ]

        modeSettingViewModel.getModeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            modeSettingViewModel.saveModeSetting(isChecked)
        }

        modeSettingViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}