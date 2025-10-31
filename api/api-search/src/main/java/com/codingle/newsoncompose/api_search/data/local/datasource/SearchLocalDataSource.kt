package com.codingle.newsoncompose.api_search.data.local.datasource

import com.codingle.newsoncompose.api_search.data.entity.SearchKeywordEntity
import com.codingle.newsoncompose.core_data.data.entity.ApiResult

interface SearchLocalDataSource {
    suspend fun insertSearchKeyword(keyword: SearchKeywordEntity): ApiResult<Unit>
    suspend fun getKeywords(): ApiResult<List<SearchKeywordEntity>>
    suspend fun deleteKeyword(keyword: SearchKeywordEntity): ApiResult<Unit>
    suspend fun deleteKeywords(): ApiResult<Unit>
}