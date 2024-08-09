package com.codingle.newsoncompose.api_search.domain.get

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetKeywordsUseCase {
    operator fun invoke(): Flow<ApiResult<List<SearchKeywordDto>>>
}