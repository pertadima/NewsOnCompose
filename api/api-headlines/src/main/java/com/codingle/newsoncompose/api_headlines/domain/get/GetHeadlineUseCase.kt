package com.codingle.newsoncompose.api_headlines.domain.get

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetHeadlineUseCase {
    operator fun invoke(source: String = ""): Flow<ApiResult<List<HeadlineArticleDto>>>
}