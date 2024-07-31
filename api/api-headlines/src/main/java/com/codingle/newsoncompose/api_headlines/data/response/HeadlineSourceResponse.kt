package com.codingle.newsoncompose.api_headlines.data.response

import com.google.gson.annotations.SerializedName

data class HeadlineSourceResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
)