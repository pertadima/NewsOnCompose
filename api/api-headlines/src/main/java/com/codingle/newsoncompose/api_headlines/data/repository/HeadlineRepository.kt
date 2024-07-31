package com.codingle.newsoncompose.api_headlines.data.repository

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {
    fun getRemoteHeadlines(): Flow<ApiResult<List<HeadlineArticleDto>>>
    fun getHeadlines(): Flow<ApiResult<List<HeadlineArticleDto>>>
}