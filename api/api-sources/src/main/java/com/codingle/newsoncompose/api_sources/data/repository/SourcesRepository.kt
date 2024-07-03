package com.codingle.newsoncompose.api_sources.data.repository

import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface SourcesRepository {
    fun getRemoteSources(): Flow<ApiResult<List<SourceDto>>>
    fun getSources(): Flow<ApiResult<List<SourceDto>>>
}