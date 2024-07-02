package com.codingle.newsoncompose.api_sources.domain.get

import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetSourcesUseCase {
    operator fun invoke(): Flow<ApiResult<List<SourceDto>>>
}