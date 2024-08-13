package com.codingle.newsoncompose.api_headlines.data.repository

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity
import com.codingle.newsoncompose.api_headlines.data.local.datasource.HeadlineLocalDataSource
import com.codingle.newsoncompose.api_headlines.data.remote.datasource.HeadlineRemoteDataSource
import com.codingle.newsoncompose.api_headlines.data.response.HeadlineResponse
import com.codingle.newsoncompose.core_data.base.BaseRepository
import com.codingle.newsoncompose.core_data.data.entity.ApiResult
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Error
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Loading
import com.codingle.newsoncompose.core_data.data.entity.ApiResult.Success
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.localResultFlow
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.remoteResultFlow
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.resultFlow
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HeadlineRepositoryImpl @Inject constructor(
    private val localDataSource: HeadlineLocalDataSource,
    private val remoteDataSource: HeadlineRemoteDataSource
) : BaseRepository(), HeadlineRepository {

    override fun getRemoteHeadlines() = remoteResultFlow { remoteDataSource.getHeadline() }.mapToEntity(
        transformData = { response -> response?.articles.orEmpty().map { it.mapToDto() } },
        saveCallResult = { data, _ -> localDataSource.insertAllHeadline(data.map { it.mapToEntity() }) }
    )

    override fun getHeadlines(source: String) = when (source.isEmpty()) {
        true -> getAllHeadlines()
        else -> localResultFlow { localDataSource.getHeadlines(source) }.mapToEntity(
            transformData = { data ->
                when (data) {
                    is List<*> -> data.map { it.mapToDto() }
                    else -> emptyList()
                }
            },
        )
    }

    override fun searchHeadlines(query: String): Flow<ApiResult<List<HeadlineArticleDto>>> = flow {
        val remoteData = remoteResultFlow { remoteDataSource.searchHeadline(query) }.mapToEntity(
            transformData = { response -> response?.articles.orEmpty().map { it.mapToDto() } },
            saveCallResult = { data, _ -> localDataSource.insertAllHeadline(data.map { it.mapToEntity() }) }
        )
        val localData = localResultFlow { localDataSource.searchHeadline(query) }.mapToEntity(
            transformData = { response -> response.orEmpty().map { it.mapToDto() } },
        )
        combine(remoteData, localData) { remote, local ->
            if (remote is Success && local is Success) {
                val combinedData = (local.data.orEmpty() + remote.data.orEmpty()).distinctBy { it.title }
                Success(combinedData, false)
            } else if (local is Success) Success(local.data.orEmpty(), false)
            else if (remote is Success) Success(remote.data.orEmpty(), true)
            else if (remote is Error && local is Error) Error(remote.error)
            else Loading
        }.collect { emit(it) }
    }.flowOn(IO)

    private fun getAllHeadlines() = resultFlow(
        networkCall = { remoteDataSource.getHeadline() },
        localCall = { localDataSource.getAllHeadlines() }
    ).mapToEntity(
        transformData = { data ->
            when (data) {
                is HeadlineResponse -> data.articles?.map { it.mapToDto() }
                is List<*> -> data.map {
                    if (it is HeadlineArticleEntity) it.mapToDto()
                    else HeadlineArticleDto()
                }

                else -> emptyList()
            }
        },
        saveCallResult = { data, isRemote ->
            if (isRemote) localDataSource.insertAllHeadline(data?.map { it.mapToEntity() }.orEmpty())
        })
}