package com.codingle.newsoncompose.api_sources.data.local.datasource

import com.codingle.newsoncompose.api_sources.data.entity.SourceEntity
import com.codingle.newsoncompose.core_data.data.entity.ApiResult

interface SourcesLocalDataSource {
    suspend fun insertAllSources(sources: List<SourceEntity>)
    suspend fun getAllSources(): ApiResult<List<SourceEntity>?>
}