package com.codingle.newsoncompose.api_settings.data.preference

import kotlinx.coroutines.flow.Flow

interface SettingPreferenceManager {
    suspend fun updateIsDarkModePreference(isDarkMode: Boolean): Flow<Boolean>
    suspend fun isDarkMode(): Flow<Boolean>
}