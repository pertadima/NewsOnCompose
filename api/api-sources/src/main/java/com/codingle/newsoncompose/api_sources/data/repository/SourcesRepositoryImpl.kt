package com.codingle.newsoncompose.api_sources.data.repository

import com.codingle.newsoncompose.api_sources.data.dto.SourceDto
import com.codingle.newsoncompose.api_sources.data.entity.SourceEntity
import com.codingle.newsoncompose.api_sources.data.local.datasource.SourcesLocalDataSource
import com.codingle.newsoncompose.api_sources.data.remote.datasource.SourcesRemoteDataSource
import com.codingle.newsoncompose.api_sources.data.response.SourcesResponse
import com.codingle.newsoncompose.core_data.base.BaseRepository
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.remoteResultFlow
import com.codingle.newsoncompose.core_data.util.CoroutineResultHandler.resultFlow
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(
    private val localDataSource: SourcesLocalDataSource,
    private val remoteDataSource: SourcesRemoteDataSource
) : BaseRepository(), SourcesRepository {

    override fun getRemoteSources() =
        remoteResultFlow { remoteDataSource.getSources() }.mapToEntity(
            transformData = { response -> response?.sources.orEmpty().map { it.mapToDto() } },
            saveCallResult = { data, _ -> localDataSource.insertAllSources(data.map { it.mapToEntity() }) }
        )

    override fun getSources() = resultFlow(
        networkCall = { remoteDataSource.getSources() },
        localCall = { localDataSource.getAllSources() }
    ).mapToEntity(
        transformData = { data ->
            when (data) {
                is SourcesResponse -> data.sources?.map { it.mapToDto() }
                is List<*> -> data.map {
                    if (it is SourceEntity) it.mapToDto()
                    else SourceDto()
                }

                else -> emptyList()
            }
        },
        saveCallResult = { data, isRemote ->
            if (isRemote) localDataSource.insertAllSources(data?.map { it.mapToEntity() }.orEmpty())
        })
}