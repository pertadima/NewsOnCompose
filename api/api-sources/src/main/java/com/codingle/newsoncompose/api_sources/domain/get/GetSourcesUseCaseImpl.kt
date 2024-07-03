package com.codingle.newsoncompose.api_sources.domain.get

import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.api_sources.data.repository.SourcesRepository
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSourcesUseCaseImpl @Inject constructor(
    private val repo: SourcesRepository
) : GetSourcesUseCase {
    override fun invoke(): Flow<ApiResult<List<SourceDto>>> = repo.getSources()
}
