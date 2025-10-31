package com.codingle.newsoncompose.api_search.data.repository

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun insertKeyword(data: SearchKeywordDto): Flow<ApiResult<Unit>>
    fun getKeywords(): Flow<ApiResult<List<SearchKeywordDto>>>
    fun deleteKeyword(data: SearchKeywordDto): Flow<ApiResult<Unit>>
    fun deleteKeywords(): Flow<ApiResult<Unit>>
}