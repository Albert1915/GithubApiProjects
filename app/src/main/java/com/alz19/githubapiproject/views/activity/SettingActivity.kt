package com.alz19.githubapiproject.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.alz19.githubapiproject.R
import com.alz19.githubapiproject.helper.SettingPreferences
import com.alz19.githubapiproject.helper.ViewModelFactory
import com.alz19.githubapiproject.helper.dataStore
import com.alz19.githubapiproject.view_models.SettingViewModel
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingActivity : AppCompatActivity() {

    private lateinit var switchTheme: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initComponent()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, ViewModelFactory.ViewModelFactorySettingPreferences(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }

    private fun initComponent() {
        switchTheme = findViewById(R.id.switch_theme)
    }
}