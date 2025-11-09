package com.codingle.newsoncompose.api_search.data.repository

import com.codingle.newsoncompose.api_search.data.dto.SearchKeywordDto
import com.codingle.newsoncompose.api_search.data.local.datasource.SearchLocalDataSource
import com.codingle.newsoncompose.core_data.base.BaseRepository
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.localApiResultFlow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localDataSource: SearchLocalDataSource
) : BaseRepository(), SearchRepository {
    override fun insertKeyword(data: SearchKeywordDto) = localApiResultFlow {
        localDataSource.insertSearchKeyword(keyword = data.mapToEntity())
    }

    override fun getKeywords() = localApiResultFlow {
        localDataSource.getKeywords()
    }.mapToEntity(transformData = {
        it.orEmpty().filter { data -> data.keyword != "" }.map { data -> data.mapToDto() }
    })

    override fun deleteKeyword(data: SearchKeywordDto) = localApiResultFlow {
        localDataSource.deleteKeyword(data.mapToEntity())
    }

    override fun deleteKeywords() = localApiResultFlow {
        localDataSource.deleteKeywords()
    }
}