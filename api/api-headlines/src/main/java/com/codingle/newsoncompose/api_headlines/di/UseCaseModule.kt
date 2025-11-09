package com.codingle.newsoncompose.api_headlines.di

import com.codingle.newsoncompose.api_headlines.data.repository.HeadlineRepository
import com.codingle.newsoncompose.api_headlines.domain.get.GetFavoriteHeadlineUseCase
import com.codingle.newsoncompose.api_headlines.domain.get.GetFavoriteHeadlineUseCaseImpl
import com.codingle.newsoncompose.api_headlines.domain.get.GetHeadlineUseCase
import com.codingle.newsoncompose.api_headlines.domain.get.GetHeadlineUseCaseImpl
import com.codingle.newsoncompose.api_headlines.domain.get.GetRemoteHeadlineUseCase
import com.codingle.newsoncompose.api_headlines.domain.get.GetRemoteHeadlineUseCaseImpl
import com.codingle.newsoncompose.api_headlines.domain.search.SearchHeadlineUseCase
import com.codingle.newsoncompose.api_headlines.domain.search.SearchHeadlineUseCaseImpl
import com.codingle.newsoncompose.api_headlines.domain.update.UpdateIsFavoriteHeadlineUseCase
import com.codingle.newsoncompose.api_headlines.domain.update.UpdateIsFavoriteHeadlineUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    internal fun provideGetHeadlinesUseCase(
        repository: HeadlineRepository
    ): GetHeadlineUseCase = GetHeadlineUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideSearchHeadlinesUseCase(
        repository: HeadlineRepository
    ): SearchHeadlineUseCase = SearchHeadlineUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideGetRemoteHeadlinesUseCase(
        repository: HeadlineRepository
    ): GetRemoteHeadlineUseCase = GetRemoteHeadlineUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideGetFavoriteHeadlinesUseCase(
        repository: HeadlineRepository
    ): GetFavoriteHeadlineUseCase = GetFavoriteHeadlineUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideUpdateIsFavoriteHeadlineUseCase(
        repository: HeadlineRepository
    ): UpdateIsFavoriteHeadlineUseCase = UpdateIsFavoriteHeadlineUseCaseImpl(repository)
}