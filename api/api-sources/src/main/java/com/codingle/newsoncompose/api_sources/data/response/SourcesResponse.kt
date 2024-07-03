package com.codingle.newsoncompose.api_sources.data.response

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("sources") val sources: List<SourceResponse>?,
    @SerializedName("status") val status: String?
)