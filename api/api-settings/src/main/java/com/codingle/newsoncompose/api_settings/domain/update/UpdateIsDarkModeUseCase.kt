package com.codingle.newsoncompose.api_settings.domain.update

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface UpdateIsDarkModeUseCase {
    operator fun invoke(isDarkMode: Boolean): Flow<ApiResult<Boolean>>
}