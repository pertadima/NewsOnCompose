package com.codingle.newsoncompose.api_settings.data.repository

import com.codingle.newsoncompose.api_settings.data.local.datasource.SettingLocalDataSource
import com.codingle.newsoncompose.core_data.base.BaseRepository
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.localResultFlow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val localDataSource: SettingLocalDataSource
) : BaseRepository(), SettingRepository {
    override fun isDarkMode() = localResultFlow {
        localDataSource.isDarkMode()
    }

    override fun updateIsDarkModePreference(isDarkMode: Boolean) = localResultFlow {
        localDataSource.updateIsDarkModePreference(isDarkMode)
    }
}
