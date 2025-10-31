package com.codingle.newsoncompose.api_search.data.local.datasource

import com.codingle.newsoncompose.api_search.data.SearchDatabase
import com.codingle.newsoncompose.api_search.data.entity.SearchKeywordEntity
import com.codingle.newsoncompose.core_data.base.BaseDataSource
import javax.inject.Inject

class SearchLocalDataSourceImpl @Inject constructor(
    private val database: SearchDatabase
) : BaseDataSource(), SearchLocalDataSource {

    override suspend fun insertSearchKeyword(keyword: SearchKeywordEntity) =
        getLocalDataWithSingleObject { database.searchDao().insertKeyword(keyword) }

    override suspend fun getKeywords() = getLocalDataWithSingleObject { database.searchDao().getAllSearchKeyword() }

    override suspend fun deleteKeyword(keyword: SearchKeywordEntity) = getLocalDataWithSingleObject {
        database.searchDao().deleteKeyword(keyword)
    }

    override suspend fun deleteKeywords() = getLocalDataWithSingleObject {
        database.searchDao().deleteKeywords()
    }
}