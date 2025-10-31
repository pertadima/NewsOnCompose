package com.codingle.newsoncompose.api_search.domain.delete

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.api_search.data.repository.SearchRepository
import javax.inject.Inject

class DeleteKeywordsUseCaseImpl @Inject constructor(
    private val repo: SearchRepository
) : DeleteKeywordsUseCase {
    override fun invoke() = repo.deleteKeywords()
    override fun invoke(keywordDto: SearchKeywordDto) = repo.deleteKeyword(keywordDto)
}
