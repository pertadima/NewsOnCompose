package com.codingle.newsoncompose.api_headlines.di

import com.codingle.newsoncompose.api_headlines.data.local.datasource.HeadlineLocalDataSource
import com.codingle.newsoncompose.api_headlines.data.remote.datasource.HeadlineRemoteDataSource
import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepositoryImpl
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
    internal fun provideHeadlineRepository(
        localDataSource: HeadlineLocalDataSource,
        remoteDataSource: HeadlineRemoteDataSource
    ): HeadlineRepository = HeadlineRepositoryImpl(localDataSource, remoteDataSource)
}