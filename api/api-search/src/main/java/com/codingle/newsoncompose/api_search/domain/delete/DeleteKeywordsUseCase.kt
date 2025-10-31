package com.codingle.newsoncompose.api_search.domain.delete

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface DeleteKeywordsUseCase {
    operator fun invoke(): Flow<ApiResult<Unit>>
    operator fun invoke(keywordDto: SearchKeywordDto): Flow<ApiResult<Unit>>
}