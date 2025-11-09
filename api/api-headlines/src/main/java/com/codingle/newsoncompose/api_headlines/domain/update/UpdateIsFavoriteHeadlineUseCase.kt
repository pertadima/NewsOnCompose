package com.codingle.newsoncompose.api_headlines.domain.update

import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface UpdateIsFavoriteHeadlineUseCase {
    operator fun invoke(isFavorite: Boolean, title: String): Flow<ApiResult<Unit>>
}