package com.codingle.newsoncompose.api_search.domain.save

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.api_search.data.repository.SearchRepository
import javax.inject.Inject

class SaveKeywordUseCaseImpl @Inject constructor(
    private val repo: SearchRepository
) : SaveKeywordUseCase {
    override fun invoke(keyword: String) = repo.insertKeyword(SearchKeywordDto(keyword))
}