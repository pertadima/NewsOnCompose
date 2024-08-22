package com.codingle.newsoncompose.api_headlines.domain.get

import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import javax.inject.Inject

class GetRemoteHeadlineUseCaseImpl @Inject constructor(
    private val repo: HeadlineRepository
) : GetRemoteHeadlineUseCase {
    override fun invoke() = repo.getRemoteHeadlines()
}
