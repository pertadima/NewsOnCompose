package com.codingle.newsoncompose.api_headlines.data.remote.datasource

import com.codingle.newsoncompose.api_headlines.data.remote.api.HeadlineApi
import com.codingle.newsoncompose.core_data.base.BaseDataSource
import javax.inject.Inject

class HeadlineRemoteDataSourceImpl @Inject constructor(
    private val api: HeadlineApi
) : BaseDataSource(), HeadlineRemoteDataSource {
    override suspend fun getHeadline() = getResultWithSingleObject { api.getHeadline() }
}