package com.codingle.newsoncompose.api_search.domain.get

import com.codingle.newsoncompose.api_search.data.repository.SearchRepository
import javax.inject.Inject

class GetKeywordsUseCaseImpl @Inject constructor(
    private val repo: SearchRepository
) : GetKeywordsUseCase {
    override fun invoke() = repo.getKeywords()
}
