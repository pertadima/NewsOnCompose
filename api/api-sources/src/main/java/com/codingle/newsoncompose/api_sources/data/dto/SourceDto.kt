package com.codingle.newsoncompose.api_sources.data.dto

import com.codingle.newsoncompose.api_sources.data.entity.SourceEntity

data class SourceDto(
    val category: String? = "",
    val country: String? = "",
    val description: String? = "",
    val id: String? = "",
    val language: String? = "",
    val name: String? = "",
    val url: String? = ""
) {
    fun mapToEntity() = SourceEntity(
        id = id.orEmpty(),
        category = category.orEmpty(),
        country = country.orEmpty(),
        description = description.orEmpty(),
        language = language.orEmpty(),
        name = name.orEmpty(),
        url = url.orEmpty()
    )
}