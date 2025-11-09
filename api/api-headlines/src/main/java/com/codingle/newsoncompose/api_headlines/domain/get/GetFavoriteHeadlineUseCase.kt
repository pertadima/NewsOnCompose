package com.codingle.newsoncompose.api_headlines.domain.get

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface GetFavoriteHeadlineUseCase {
    operator fun invoke(): Flow<ApiResult<List<HeadlineArticleDto>>>
}