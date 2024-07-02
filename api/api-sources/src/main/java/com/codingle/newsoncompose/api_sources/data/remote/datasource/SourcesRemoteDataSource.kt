package com.codingle.newsoncompose.api_sources.data.remote.datasource

import com.codingle.newsoncompose.api_sources.data.response.SourcesResponse
import com.codingle.newsoncompose.core_data.data.entity.ApiResult

interface SourcesRemoteDataSource {
    suspend fun getSources(): ApiResult<SourcesResponse>
}