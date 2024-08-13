package com.codingle.newsoncompose.api_headlines.data.remote.api

import com.codingle.newsoncompose.api_headlines.data.response.HeadlineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface HeadlineApi {
    @GET("v2/top-headlines?language=en&pageSize=100")
    suspend fun getHeadline(): Response<HeadlineResponse>

    @GET("v2/top-headlines?language=en&pageSize=100")
    suspend fun searchHeadline(@Query("q") query: String): Response<HeadlineResponse>
}