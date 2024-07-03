package com.codingle.newsoncompose.api_sources.data.local.datasource

import com.codingle.newsoncompose.api_sources.data.entity.SourceEntity
import com.codingle.newsoncompose.api_sources.data.local.SourcesDatabase
import com.codingle.newsoncompose.core_data.base.BaseDataSource
import javax.inject.Inject

class SourcesLocalDataSourceImpl @Inject constructor(
    private val database: SourcesDatabase
) : BaseDataSource(), SourcesLocalDataSource {

    override suspend fun insertAllSources(sources: List<SourceEntity>) = database.sourceDao().insertAllSources(sources)

    override suspend fun getAllSources() = getLocalDataWithSingleObject { database.sourceDao().getAllSources() }
}