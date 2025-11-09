package com.codingle.newsoncompose.api_settings.domain.get

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetIsDarkModeUseCase {
    operator fun invoke(): Flow<ApiResult<Boolean>>
}