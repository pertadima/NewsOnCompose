package com.codingle.newsoncompose.api_search.di

import com.codingle.newsoncompose.api_search.data.repository.SearchRepository
import com.codingle.newsoncompose.api_search.domain.delete.DeleteKeywordsUseCase
import com.codingle.newsoncompose.api_search.domain.delete.DeleteKeywordsUseCaseImpl
import com.codingle.newsoncompose.api_search.domain.get.GetKeywordsUseCase
import com.codingle.newsoncompose.api_search.domain.get.GetKeywordsUseCaseImpl
import com.codingle.newsoncompose.api_search.domain.save.SaveKeywordUseCase
import com.codingle.newsoncompose.api_search.domain.save.SaveKeywordUseCaseImpl
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
    internal fun provideGetKeywordsUseCase(
        repository: SearchRepository
    ): GetKeywordsUseCase = GetKeywordsUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideSaveKeywordUseCase(
        repository: SearchRepository
    ): SaveKeywordUseCase = SaveKeywordUseCaseImpl(repository)

    @Provides
    @Singleton
    internal fun provideDeleteKeywordsUseCase(
        repository: SearchRepository
    ): DeleteKeywordsUseCase = DeleteKeywordsUseCaseImpl(repository)
}