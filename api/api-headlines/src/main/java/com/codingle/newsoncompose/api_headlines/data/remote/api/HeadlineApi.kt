package com.codingle.newsoncompose.api_headlines.data.remote.api

import com.codingle.newsoncompose.api_headlines.data.response.HeadlineResponse
import retrofit2.Response
import retrofit2.http.GET


interface HeadlineApi {
    @GET("v2/top-headlines?language=en&pageSize=100")
    suspend fun getHeadline(): Response<HeadlineResponse>
}