package com.codingle.newsoncompose.api_sources.data.remote.api

import com.codingle.newsoncompose.api_sources.data.response.SourcesResponse
import retrofit2.Response
import retrofit2.http.GET

interface SourcesApi {
    @GET("v2/top-headlines/sources")
    suspend fun getHeadlineSources(): Response<SourcesResponse>
}