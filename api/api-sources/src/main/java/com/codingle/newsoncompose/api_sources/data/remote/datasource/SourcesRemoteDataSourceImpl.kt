package com.codingle.newsoncompose.api_sources.data.remote.datasource

import com.codingle.newsoncompose.api_sources.data.remote.api.SourcesApi
import com.codingle.newsoncompose.core_data.base.BaseDataSource
import javax.inject.Inject

class SourcesRemoteDataSourceImpl @Inject constructor(
    private val api: SourcesApi
) : BaseDataSource(), SourcesRemoteDataSource {

    override suspend fun getSources() = getResultWithSingleObject { api.getHeadlineSources() }
}