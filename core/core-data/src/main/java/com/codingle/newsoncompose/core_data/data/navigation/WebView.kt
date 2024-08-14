package com.codingle.newsoncompose.core_data.data.navigation

import kotlinx.serialization.Serializable

@Serializable
data class WebView(
    val title: String,
    val url: String
)