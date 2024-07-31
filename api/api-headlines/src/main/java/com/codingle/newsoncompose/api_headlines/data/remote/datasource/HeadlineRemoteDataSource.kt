package com.codingle.newsoncompose.api_headlines.data.remote.datasource

import com.codingle.newsoncompose.api_headlines.data.response.HeadlineResponse
import com.codingle.newsoncompose.core_data.data.entity.ApiResult

interface HeadlineRemoteDataSource {
    suspend fun getHeadline(): ApiResult<HeadlineResponse>
}