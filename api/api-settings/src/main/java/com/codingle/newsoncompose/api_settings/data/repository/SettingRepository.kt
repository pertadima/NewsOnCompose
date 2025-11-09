package com.codingle.newsoncompose.api_settings.data.repository

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun isDarkMode(): Flow<ApiResult<Boolean>>
    fun updateIsDarkModePreference(isDarkMode: Boolean): Flow<ApiResult<Boolean>>
}