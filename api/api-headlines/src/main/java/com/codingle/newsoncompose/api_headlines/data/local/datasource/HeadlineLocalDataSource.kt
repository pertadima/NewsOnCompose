package com.codingle.newsoncompose.api_headlines.data.local.datasource

import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity
import com.codingle.newsoncompose.core_data.data.entity.ApiResult

interface HeadlineLocalDataSource {
    suspend fun insertAllHeadline(sources: List<HeadlineArticleEntity>)
    suspend fun getAllHeadlines(): ApiResult<List<HeadlineArticleEntity>?>
    suspend fun getHeadlines(source: String): ApiResult<List<HeadlineArticleEntity>?>
    suspend fun searchHeadline(query: String): ApiResult<List<HeadlineArticleEntity>?>
}