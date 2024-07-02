package com.codingle.newsoncompose.api_sources.di

import com.codingle.newsoncompose.api_sources.data.repository.SourcesRepository
import com.codingle.newsoncompose.api_sources.domain.get.GetSourcesUseCase
import com.codingle.newsoncompose.api_sources.domain.get.GetSourcesUseCaseImpl
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
    internal fun provideGetSourcesUseCase(
        repository: SourcesRepository
    ): GetSourcesUseCase = GetSourcesUseCaseImpl(repository)
}