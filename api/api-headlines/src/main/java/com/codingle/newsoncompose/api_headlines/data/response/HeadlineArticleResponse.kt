package com.codingle.newsoncompose.api_headlines.data.response

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.google.gson.annotations.SerializedName

data class HeadlineArticleResponse(
    @SerializedName("author") val author: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("source") val source: HeadlineSourceResponse?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?
) {
    fun mapToDto() = HeadlineArticleDto(
        author = author.orEmpty(),
        content = content.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = source?.name.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty()
    )
}