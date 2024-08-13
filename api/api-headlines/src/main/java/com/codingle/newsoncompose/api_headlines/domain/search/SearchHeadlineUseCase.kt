package com.codingle.newsoncompose.api_headlines.domain.search

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface SearchHeadlineUseCase {
    operator fun invoke(query: String): Flow<ApiResult<List<HeadlineArticleDto>>>
}