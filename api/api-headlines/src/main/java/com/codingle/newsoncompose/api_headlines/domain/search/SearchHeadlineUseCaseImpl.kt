package com.codingle.newsoncompose.api_headlines.domain.search

import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import javax.inject.Inject

class SearchHeadlineUseCaseImpl @Inject constructor(
    private val repo: HeadlineRepository
) : SearchHeadlineUseCase {
    override fun invoke(query: String) = repo.searchHeadlines(query)
}
