package com.codingle.newsoncompose.api_headlines.data.dto

data class HeadlineDto(
    val articles: List<HeadlineArticleDto>,
    val status: String,
    val totalResults: Int
)