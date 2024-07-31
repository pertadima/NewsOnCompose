package com.codingle.newsoncompose.api_headlines.data.response

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineDto
import com.google.gson.annotations.SerializedName


data class HeadlineResponse(
    @SerializedName("articles") val articles: List<HeadlineArticleResponse>?,
    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResults: Int?
) {
    fun mapToDto() = HeadlineDto(
        articles = articles?.map { it.mapToDto() }.orEmpty(),
        status = status.orEmpty(),
        totalResults = totalResults ?: 0
    )
}