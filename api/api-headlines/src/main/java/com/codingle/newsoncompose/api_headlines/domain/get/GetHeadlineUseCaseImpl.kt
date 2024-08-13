package com.codingle.newsoncompose.api_headlines.domain.get

import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import javax.inject.Inject

class GetHeadlineUseCaseImpl @Inject constructor(
    private val repo: HeadlineRepository
) : GetHeadlineUseCase {
    override fun invoke(source: String) = repo.getHeadlines(source)
}
