package com.codingle.newsoncompose.api_search.domain.save

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface SaveKeywordUseCase {
    operator fun invoke(keyword: String): Flow<ApiResult<Unit>>
}