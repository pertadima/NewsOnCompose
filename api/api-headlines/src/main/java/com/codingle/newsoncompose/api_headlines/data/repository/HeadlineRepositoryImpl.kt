package com.codingle.newsoncompose.api_headlines.data.repository

import com.codingle.newsoncompose.api_headlines.data.dto.HeadlineArticleDto
import com.codingle.newsoncompose.api_headlines.data.entity.HeadlineArticleEntity
import com.codingle.newsoncompose.api_headlines.data.local.datasource.HeadlineLocalDataSource
import com.codingle.newsoncompose.api_headlines.data.remote.datasource.HeadlineRemoteDataSource
import com.codingle.newsoncompose.api_headlines.data.response.HeadlineResponse
import com.codingle.newsoncompose.core_data.base.BaseRepository
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.localResultFlow
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.remoteResultFlow
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.resultFlow
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