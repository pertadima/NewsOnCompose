package com.codingle.newsoncompose.api_headlines.data.local.datasource

import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity
import com.codingle.newsoncompose.api_headlines.data.local.HeadlineDatabase
import com.codingle.newsoncompose.core_data.base.BaseDataSource
import javax.inject.Inject

class HeadlineLocalDataSourceImpl @Inject constructor(
    private val database: HeadlineDatabase
) : BaseDataSource(), HeadlineLocalDataSource {
    override suspend fun insertAllHeadline(sources: List<HeadlineArticleEntity>) =
        database.headlineDao().insertAllHeadline(sources)

    override suspend fun getAllHeadlines() = getLocalDataWithSingleObject { database.headlineDao().getAllHeadlines() }

    override suspend fun getHeadlines(source: String) = getLocalDataWithSingleObject {
        database.headlineDao().getHeadlines(source)
    }

    override suspend fun searchHeadline(query: String) = getLocalDataWithSingleObject {
        database.headlineDao().searchHeadline(query)
    }
}