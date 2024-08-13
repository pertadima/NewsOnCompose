package com.codingle.newsoncompose.core_data.data.navigation

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val keyword: String
)