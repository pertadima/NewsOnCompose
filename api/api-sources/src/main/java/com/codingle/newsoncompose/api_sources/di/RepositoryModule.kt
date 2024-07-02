package com.codingle.newsoncompose.api_sources.di

import com.codingle.newsoncompose.api_sources.data.local.datasource.SourcesLocalDataSource
import com.codingle.newsoncompose.api_sources.data.remote.datasource.SourcesRemoteDataSource
import com.codingle.newsoncompose.api_sources.data.repository.SourcesRepository
import com.codingle.newsoncompose.api_sources.data.repository.SourcesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    internal fun provideSourceRepository(
        localDataSource: SourcesLocalDataSource,
        remoteDataSource: SourcesRemoteDataSource
    ): SourcesRepository = SourcesRepositoryImpl(localDataSource, remoteDataSource)
}