package com.codingle.newsoncompose.api_headlines.data.dto

import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity

data class HeadlineArticleDto(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: String = "",
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val isFavorite: Boolean = false
) {
    fun mapToEntity() = HeadlineArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
        isFavorite = isFavorite
    )
}