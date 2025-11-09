package com.codingle.newsoncompose.api_headlines.data.repository

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {
    fun getRemoteHeadlines(): Flow<ApiResult<List<HeadlineArticleDto>>>
    fun getHeadlines(source: String): Flow<ApiResult<List<HeadlineArticleDto>>>
    fun searchHeadlines(query: String): Flow<ApiResult<List<HeadlineArticleDto>>>
    fun getFavoriteHeadlines(): Flow<ApiResult<List<HeadlineArticleDto>>>
    fun updateIsFavoriteHeadline(isFavorite: Boolean, title: String): Flow<ApiResult<Unit>>
}