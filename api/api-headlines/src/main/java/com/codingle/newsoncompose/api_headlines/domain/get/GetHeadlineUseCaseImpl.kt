package com.codingle.newsoncompose.api_headlines.domain.get

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeadlineUseCaseImpl @Inject constructor(
    private val repo: HeadlineRepository
) : GetHeadlineUseCase {
    override fun invoke(source: String): Flow<ApiResult<List<HeadlineArticleDto>>> = repo.getHeadlines(source)
}
