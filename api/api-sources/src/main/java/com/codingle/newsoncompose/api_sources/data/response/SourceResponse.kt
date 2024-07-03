package com.codingle.newsoncompose.api_sources.data.response

import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("category") val category: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
) {
    fun mapToDto() = SourceDto(
        id = id,
        category = category,
        country = country,
        description = description,
        language = language,
        name = name,
        url = url
    )
}