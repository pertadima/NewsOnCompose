package com.codingle.newsoncompose.api_settings.data.local.datasource

import com.codingle.newsoncompose.api_settings.data.preference.SettingPreferenceManager
import com.codingle.newsoncompose.core_data.base.BaseDataSource
import javax.inject.Inject

class SettingLocalDataSourceImpl @Inject constructor(
    private val settingPreferenceManager: SettingPreferenceManager
) : BaseDataSource(), SettingLocalDataSource {
    override suspend fun isDarkMode() = settingPreferenceManager.isDarkMode()

    override suspend fun updateIsDarkModePreference(isDarkMode: Boolean) =
        settingPreferenceManager.updateIsDarkModePreference(isDarkMode)
}