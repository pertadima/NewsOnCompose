package com.codingle.newsoncompose.api_settings.data.local.datasource

import kotlinx.coroutines.flow.Flow

interface SettingLocalDataSource {
    suspend fun isDarkMode(): Flow<Boolean>
    suspend fun updateIsDarkModePreference(isDarkMode: Boolean): Flow<Boolean>
}