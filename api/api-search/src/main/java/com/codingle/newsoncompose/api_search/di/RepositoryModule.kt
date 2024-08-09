package com.codingle.newsoncompose.api_search.di

import com.codingle.newsoncompose.api_search.data.local.datasource.SearchLocalDataSource
import com.codingle.newsoncompose.api_search.data.repository.SearchRepository
import com.codingle.newsoncompose.api_search.data.repository.SearchRepositoryImpl
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
    internal fun provideSearchRepository(
        localDataSource: SearchLocalDataSource,
    ): SearchRepository = SearchRepositoryImpl(localDataSource)
}