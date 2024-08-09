package com.codingle.newsoncompose.api_search.data.dto

import com.codingle.newsoncompose.api_search.data.entity.SearchKeywordEntity

data class SearchKeywordDto(
    val keyword: String
) {
    fun mapToEntity() = SearchKeywordEntity(keyword = keyword)
}